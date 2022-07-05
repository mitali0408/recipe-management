package com.recipe.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.exception.EntityNotFoundException;
import com.recipe.model.Recipe;
import com.recipe.service.RecipeService;

/**
 * All operations with recipe management.
 * <p/>
 */
@RestController
@RequestMapping("/recipe")
public class RecipeController
{

    @Autowired
    RecipeService recipeService;


    @GetMapping("/searchrecipe")
    public List<Recipe> search(
        @RequestParam(required = false, defaultValue = "") String includeIngredients, @RequestParam(required = false, defaultValue = "") String excludeIngredients,
        @RequestParam(required = false, defaultValue = "") String dishType, @RequestParam(required = false, defaultValue = "") String instructions,
        @RequestParam(required = false, defaultValue = "0") int servingNumber)
        throws EntityNotFoundException
    {
        return recipeService.find(includeIngredients, excludeIngredients, dishType, instructions, servingNumber);
    }
    
    @GetMapping("/searchrecipe/{recipeid}")
    public Optional<Recipe> search(@PathVariable(value = "recipeid") Long recipeId)
        throws EntityNotFoundException
    {
        return recipeService.findbyRecipeId(recipeId);
    }


    @PostMapping("/createrecipe")
    public Recipe createRecipe(@RequestBody Recipe recipe) throws ConstraintViolationException
    {
        return recipeService.addNewRecipe(recipe);
    }


    @DeleteMapping("/deleterecipe/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable(value = "recipeId") Long recipeId) throws EntityNotFoundException
    {
        recipeService.deleteRecipe(recipeId);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/recipe/{recipeid}")
    public Recipe updateRecipe(@PathVariable(value = "recipeid") Long recipeId, @RequestBody Recipe updatedRecipeDetails)
        throws EntityNotFoundException
    {

        return recipeService.updateRecipe(recipeId, updatedRecipeDetails);
    }

}
