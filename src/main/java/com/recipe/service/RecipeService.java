package com.recipe.service;

import java.util.List;
import java.util.Optional;

import com.recipe.model.Recipe;

public interface RecipeService
{

    public Recipe addNewRecipe(Recipe recipe);


    public Recipe updateRecipe(Long recipeId, Recipe updateRecipe);


    public void deleteRecipe(Long recipeId);


    public List<Recipe> find(String includeIngredients, String excludeIngredients, String recipeType, String recipeInstructions, int recipeServingCount);


    public Optional<Recipe> findbyRecipeId(Long recipeId);

}
