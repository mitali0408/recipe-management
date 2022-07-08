package com.recipe.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.recipe.exception.ResourceNotFoundException;
import com.recipe.model.Recipe;
import com.recipe.service.RecipeService;

import io.swagger.annotations.ApiOperation;

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


    @ApiOperation(value="Utility to get all the recipes",
        notes="Utility to get all the recipes",
        response=ResponseEntity.class,
        httpMethod="GET",
        produces="application/json"
        )
    @GetMapping("/searchrecipe")
    public ResponseEntity<List<Recipe>> searchrecipe(
        @RequestParam(required = false, defaultValue = "") String includeIngredients, @RequestParam(required = false, defaultValue = "") String excludeIngredients,
        @RequestParam(required = false, defaultValue = "") String dishType, @RequestParam(required = false, defaultValue = "") String instructions,
        @RequestParam(required = false, defaultValue = "0") int servingNumber)

    {
         List<Recipe> recipeList = recipeService.find(includeIngredients, excludeIngredients, dishType, instructions, servingNumber);
        if (!recipeList.isEmpty())
            return ResponseEntity.ok(recipeList);
        else
        {
            return new ResponseEntity<List<Recipe>>(recipeList, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value="Utility to get all the recipes by given recipeId",
        notes="Utility to get all the recipes by given recipeId",
        response=ResponseEntity.class,
        httpMethod="GET",
        produces="application/json"
        )
    @GetMapping("/searchrecipe/{recipeid}")
    public ResponseEntity<Recipe> search(@PathVariable(value = "recipeid") Long recipeId)
    {
        return ResponseEntity.ok(recipeService.findbyRecipeId(recipeId).orElseThrow(() -> new ResourceNotFoundException("No data for given recipeId" + recipeId)));
    }

    @ApiOperation(value="Utility to add new recipe",
        notes="Utility to add new recipe",
        response=ResponseEntity.class,
        httpMethod="POST",
        produces="application/json"
        )
    @PostMapping("/addNewrecipe")
    public ResponseEntity<Recipe> addNewRecipe(@RequestBody Recipe recipe) throws ConstraintViolationException
    {
        return ResponseEntity.ok(recipeService.addNewRecipe(recipe));
    }

    @ApiOperation(value="Utility to delete recipe",
        notes="Utility to delete recipe",
        response=ResponseEntity.class,
        httpMethod="POST",
        produces="application/json"
        )
    @DeleteMapping("/deleterecipe/{recipeid}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable(value = "recipeid") Long recipeId)
        throws ResourceNotFoundException
    {
         recipeService.deleteRecipe(recipeId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value="Utility to update existing recipe",
        notes ="Utility to update existing recipe",
        response=ResponseEntity.class,
        httpMethod="POST",
        produces="application/json"
        )
    @PutMapping("/updaterecipe/{recipeid}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable(value = "recipeid") Long recipeId, @RequestBody Recipe updatedRecipeDetails)
        throws ResourceNotFoundException
    {

        return ResponseEntity.ok(recipeService.updateRecipe(recipeId, updatedRecipeDetails));
    }

}
