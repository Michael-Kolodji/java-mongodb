package challenge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	RecipeCommentRepository recipeCommentRepository;

	@Override
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	@Override
	public void update(String id, Recipe recipe) {
		Recipe recipeBase = recipeRepository.findRecipeById(id);

		if (recipeBase == null) {
			return;
		}

		recipeBase.setTitle(recipe.getTitle());
		recipeBase.setDescription(recipe.getDescription());
		recipeBase.setIngredients(recipe.getIngredients());

		recipeRepository.save(recipeBase);
	}

	@Override
	public void delete(String id) {
		recipeRepository.deleteById(id);
	}

	@Override
	public Recipe get(String id) {
		return recipeRepository.findRecipeById(id);
	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {
		return recipeRepository.findRecipeByIngredientsOrderByTitle(ingredient);
	}

	@Override
	public List<Recipe> search(String search) {
		return recipeRepository.findRecipeByTitleIgnoreCaseLikeOrDescriptionIgnoreCaseLikeOrderByTitle(search, search);
	}

	@Override
	public void like(String id, String userId) {
		Recipe recipeBase = recipeRepository.findRecipeById(id);

		if (recipeBase == null) {
			return;
		}

		recipeBase.getLikes().set(0, String.valueOf(Integer.parseInt(recipeBase.getLikes().get(0)) + 1));
		recipeBase.getLikes().set(1, userId);

		recipeRepository.save(recipeBase);
	}

	@Override
	public void unlike(String id, String userId) {
		Recipe recipeBase = recipeRepository.findRecipeById(id);

		if (recipeBase == null) {
			return;
		}

		recipeBase.getLikes().set(0, String.valueOf(Integer.parseInt(recipeBase.getLikes().get(0)) - 1));
		recipeBase.getLikes().set(1, userId);

		recipeRepository.save(recipeBase);
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {

		Recipe recipeBase = recipeRepository.findRecipeById(id);

		if (recipeBase == null) {
			return null;
		}

		RecipeComment commentSave = recipeCommentRepository.save(comment);

		if (recipeBase.getComments() != null) {
			recipeBase.getComments().add(comment);
		} else {
			ArrayList<RecipeComment> list = new ArrayList<RecipeComment>();
			list.add(comment);
			recipeBase.setComments(list);
		}

		recipeRepository.save(recipeBase);

		return commentSave;

	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {

		Recipe recipe = recipeRepository.findRecipeById(id);
		RecipeComment recipeComment = new RecipeComment();

		if (recipe == null) {
			return;
		}

		recipeComment.setId(commentId);
		recipeComment.setComment(comment.getComment());

		recipe.getComments().forEach(cmt -> {
			if (cmt.getId().equals(commentId)) {
				cmt.setComment(comment.getComment());
			}
		});

		recipeCommentRepository.save(recipeComment);
		recipeRepository.save(recipe);

	}

	@Override
	public void deleteComment(String id, String commentId) {

		Recipe recipe = recipeRepository.findRecipeById(id);

		if (recipe == null) {
			return;
		}

		recipe.getComments().stream().filter(cmt -> cmt.getId().equals(commentId)).findAny()
				.map(cmt -> recipe.getComments().remove(cmt));

		recipeRepository.save(recipe);

		recipeCommentRepository.deleteById(commentId);

	}

}
