package com.recipe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.model.Recipe;
import com.recipe.repository.RecipeRepository;

@Service
public class RecipeService
{
    @Autowired
    RecipeRepository recipeRepository;

    public Recipe addNewRecipe(Recipe recipe)
    {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long recipeId, Recipe updateRecipe)
    {
        return recipeRepository.save(updateRecipe);
    }

    public void deleteRecipe(Long recipeId)
    {
        recipeRepository.deleteById(recipeId);
    }


    public List<Recipe> find(String includeIngredients, String excludeIngredients, String recipeType, String recipeInstructions, int recipeServingCount)
    {
        if (includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeType.isEmpty() && recipeInstructions.isEmpty() && recipeServingCount == 0)
        {
            return recipeRepository.findAll();
        }
        else
        {
            if (!recipeType.isEmpty() && includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount == 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository.findByRecipeType(recipeType);
            }
            else if (recipeType.isEmpty() && !includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount == 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> recipe.getIngredients().getRecipeIngredients().contains(includeIngredients))
                    .collect(Collectors.toList());
            }
            else if (recipeType.isEmpty() && includeIngredients.isEmpty() && !excludeIngredients.isEmpty() && recipeServingCount == 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> !(recipe.getIngredients().getRecipeIngredients().contains(excludeIngredients)))
                    .collect(Collectors.toList());

            }
            else if (recipeType.isEmpty() && includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount != 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository.findByRecipeServingCount(recipeServingCount);

            }
            else if (recipeType.isEmpty() && includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount == 0 && !recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> recipe.getRecipeInstructions().contains(recipeInstructions))
                    .collect(Collectors.toList());

            }
            else if (!recipeType.isEmpty() && !includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount == 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> recipe.getIngredients().getRecipeIngredients().contains(includeIngredients) && recipe.getRecipeType().equals(recipeType))
                    .collect(Collectors.toList());

            }
            else if (!recipeType.isEmpty() && includeIngredients.isEmpty() && !excludeIngredients.isEmpty() && recipeServingCount == 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> !recipe.getIngredients().getRecipeIngredients().contains(excludeIngredients) && recipe.getRecipeType().equals(recipeType))
                    .collect(Collectors.toList());
            }
            else if (!recipeType.isEmpty() && includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount != 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository.findByRecipeTypeAndRecipeServingCount(recipeType, recipeServingCount);
            }
            else if (!recipeType.isEmpty() && includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount == 0 && !recipeInstructions.isEmpty())
            {
                    return recipeRepository.findByRecipeTypeAndRecipeInstructions(recipeType, recipeInstructions);
            }
              else if (recipeType.isEmpty() && !includeIngredients.isEmpty() && excludeIngredients.isEmpty() && recipeServingCount != 0 && recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> recipe.getIngredients().getRecipeIngredients().contains(excludeIngredients) && recipe.getRecipeServingCount() == recipeServingCount)
                    .collect(Collectors.toList());
            }
            else if (recipeType.isEmpty() && includeIngredients.isEmpty() && !excludeIngredients.isEmpty() && recipeServingCount == 0 && !recipeInstructions.isEmpty())
            {
                    return recipeRepository
                    .findAll().stream()
                    .filter(recipe -> !recipe.getIngredients().getRecipeIngredients().contains(excludeIngredients) && recipe.getRecipeInstructions().equals(recipeInstructions))
                    .collect(Collectors.toList());
            }
        }
        return null;
    }

    public Optional<Recipe> findbyRecipeId(Long recipeId)
    {
        return recipeRepository.findById(recipeId);
    }
  

}
