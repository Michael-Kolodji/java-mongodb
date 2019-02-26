package challenge;

import org.springframework.data.repository.CrudRepository;

public interface RecipeCommentRepository extends CrudRepository<RecipeComment, String>{

	RecipeComment findRecipeCommentById(String id);
	
}
