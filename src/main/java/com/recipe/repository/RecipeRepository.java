package com.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>
{

    List<Recipe> findByRecipeType(String recipeType);


    List<Recipe> findByRecipeServingCount(int recipeServingCount);


    List<Recipe> findByRecipeTypeAndRecipeServingCount(String recipeType, int recipeServingCount);


    List<Recipe> findByRecipeTypeAndRecipeInstructions(String recipeType, String recipeInstructions);
}
