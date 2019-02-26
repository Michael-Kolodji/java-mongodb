package challenge;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

	Recipe findRecipeById(String id);
	
	List<Recipe> findRecipeByIngredientsOrderByTitle(String ingredient);
	
	List<Recipe> findRecipeByTitleIgnoreCaseLikeOrDescriptionIgnoreCaseLikeOrderByTitle(String search1, String search2);
	
}
