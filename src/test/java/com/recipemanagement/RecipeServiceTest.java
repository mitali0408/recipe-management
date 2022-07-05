package com.recipemanagement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipe.exception.ConstraintsViolationException;
import com.recipe.exception.EntityNotFoundException;
import com.recipe.model.Ingredients;
import com.recipe.model.Recipe;
import com.recipe.repository.RecipeRepository;
import com.recipe.service.RecipeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=RecipeService.class)
public class RecipeServiceTest
{

    @MockBean
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    private static long recipeId;
    private static String recipeName;
    private static String recipeType;
    private static String recipeInstructions;
    private static int recipeServingCount;
    private static Ingredients ingredients;
    private static long recipeIdNotExisting;
    private static String recipeIngredients;


    @BeforeClass
    public static void init()
    {
        recipeId = 1;
        recipeName = " Chicken Pasta";
        recipeType = "non-veg";
        recipeInstructions = "cook in  oven";
        recipeServingCount = 3;
        recipeIdNotExisting = 4;
        recipeIngredients = "Chicken, flour";
       // ingredients.setRecipeIngredients(recipeIngredients);

    }


    @Before(value = "")
    public void setup()
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(generateRecipeForMock(recipeId));
        Mockito.when(recipeRepository.findByRecipeType(recipeType)).thenReturn(generateRecipeForMock(recipeId));
        Mockito.when(recipeRepository.findByRecipeServingCount(recipeServingCount)).thenReturn(generateRecipeForMock(recipeId));
        Mockito.when(recipeRepository.save(new Recipe())).thenReturn(generateSingleRecipeForMock(recipeId));
    }


    //Tests mainly Covering the Service Layer
    @Test
    public void whenFindrecipeByRecipeType_thenCorrect() throws EntityNotFoundException
    {

        List<Recipe> recipeList = recipeRepository.findByRecipeServingCount(recipeServingCount);
       for(Recipe recipe:recipeList) {

        assertThat(recipe.getId(), is(recipeId));
        assertThat(recipe.getRecipeInstructions(), is(recipeInstructions));
        assertThat(recipe.getRecipeName(), is(recipeName));
        assertThat(recipe.getRecipeServingCount(), is(recipeServingCount));
        assertThat(recipe.getIngredients().getRecipeIngredients(), is(recipeIngredients));
        assertThat(recipe.getRecipeType(), is(recipeType));
       }

    }


    /*  @Test(expected = EntityNotFoundException.class)
    public void whenFindrecipeByNonExistingId_thenExcption() throws EntityNotFoundException
    {
    
        Long recipeId = 1l;
    
        Recipe Recipe = recipeService.find(recipeId);
    
    }
    */

    @Test
    public void whenSaverecipe_thenCorrectCreatedId() throws ConstraintsViolationException
    {
        setUpSaveException();
        Recipe recipeCreated = null ;
        for(Recipe recipe : generateRecipeForMock(1l)) {
            recipeCreated= recipeService.addNewRecipe(recipe);
    }
        assertThat(recipeCreated.getId(), is(recipeId));
    }

    /*  //This covers all the DataIntegrityViolationException from DAO layer
    @Test(expected = ConstraintsViolationException.class)
    public void whenSaverecipe_givenDuplicateLicensePlate_thenException() throws ConstraintsViolationException
    {
        setUpSaveException();
    
        Recipe recipe = new Recipe();
        Recipe recipeCreated = recipeService.addNewRecipe(recipe);
    
    }*/


    //DAO Data
    private List<Recipe> generateRecipeForMock(long id)
    {
        List<Recipe> recipeList = new ArrayList<>();

        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1l);
        ingredient.setRecipeIngredients("chicken,flour");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeServingCount(4);
        recipe.setId(1L);
        recipeList.add(recipe);
        return recipeList;
    }
    private Recipe generateSingleRecipeForMock(long id)
    {
        List<Recipe> recipeList = new ArrayList<>();

        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1l);
        ingredient.setRecipeIngredients("chicken,flour");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeServingCount(4);
        recipe.setId(1L);
        return recipe;
    }

    private void setUpSaveException()
    {
        Recipe recipeDuplicate = new Recipe();
        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1l);
        ingredient.setRecipeIngredients("chicken,flour");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeServingCount(4);
        recipe.setId(1L);
        Mockito.doThrow(new DataIntegrityViolationException("Invalid Data")).when(recipeRepository).save(recipeDuplicate);
    }
}
