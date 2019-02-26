package challenge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	private RecipeService service;

	@PostMapping
	@ResponseBody
	public Recipe save(@RequestBody Recipe recipe) {
		return service.save(recipe);
	}

	@PutMapping("/{id}")
	public void update(@RequestBody Recipe recipe, @PathVariable String id) {
		service.update(id, recipe);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		service.delete(id);
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Recipe get(@PathVariable String id) {
		return service.get(id);
	}

	@GetMapping("/ingredient")
	@ResponseBody
	public List<Recipe> listByIngredient(@RequestParam String ingredient) {
		return service.listByIngredient(ingredient);
	}

	@GetMapping("/search")
	@ResponseBody
	public List<Recipe> search(@RequestParam String search) {
		return service.search(search);
	}

	@PostMapping("/{id}/like/{userId}")
	public void like(@PathVariable("id") String id, @PathVariable("userId") String userId) {
		service.like(id, userId);
	}

	@DeleteMapping("/{id}/like/{userId}")
	public void unlike(@PathVariable("id") String id, @PathVariable("userId") String userId) {
		service.unlike(id, userId);
	}

	@PostMapping("/{id}/comment")
	@ResponseBody
	public RecipeComment addComment(@PathVariable String id, @RequestBody RecipeComment comment) {
		return service.addComment(id, comment);
	}

	@PutMapping("/{id}/comment/{commentId}")
	public void updateComment(@PathVariable("id") String id, @PathVariable("commentId") String commentId, @RequestBody RecipeComment comment) {
		service.updateComment(id, commentId, comment);
	}

	@DeleteMapping("/{id}/comment/{commentId}")
	public void deleteComment(@PathVariable("id") String id, @PathVariable("commentId") String commentId) {
		service.deleteComment(id, commentId);
	}

}
