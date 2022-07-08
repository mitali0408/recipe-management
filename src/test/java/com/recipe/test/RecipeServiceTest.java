package com.recipe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipe.exception.ConstraintsViolationException;
import com.recipe.exception.ResourceNotFoundException;
import com.recipe.model.Ingredients;
import com.recipe.model.Recipe;
import com.recipe.repository.RecipeRepository;
import com.recipe.service.RecipeService;
import com.recipe.service.impl.RecipeServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipeService.class)
public class RecipeServiceTest
{
    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    private static List<Recipe> vegRecipeList;
    private static List<Recipe> nonVegRecipeList;
    static Recipe expectedVegRecipe;
    static Recipe expectedNonVegRecipe;
    static List<Recipe> allRecipe;
    private static long recipeId;
    private static String recipeName;
    private static String vegRecipeType;
    private static String nonVegRecipeType;
    private static String recipeInstructions;
    private static int recipeServingCount;
    private static Ingredients ingredients;
    private static long recipeIdNotExisting;
    private static String nonVegRecipeIngredients;
    private static String vegRecipeIngredients;
    private static int actualDataPresent;


    @BeforeClass
    public static void init()
    {

        nonVegRecipeList = new ArrayList<>();
        vegRecipeList = new ArrayList<>();
        allRecipe = new ArrayList<>();
        recipeId = 1;
        recipeName = " Chicken Pasta";
        vegRecipeType = "veg";
        nonVegRecipeType = "non-veg";
        recipeInstructions = "cook in oven";
        recipeServingCount = 3;
        recipeIdNotExisting = 4;
        nonVegRecipeIngredients = "chicken,pasta";
        vegRecipeIngredients = "matar,paneer";
        actualDataPresent = 2;
        prepareRecipeData();

        // ingredients.setRecipeIngredients(recipeIngredients);

    }


    @Before
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
       // Mockito.when(recipeRepository.deleteById(Mockito.anyLong())).thenReturn(" ");
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(generateSingleOptionalRecipeForMock(1L));
        Mockito.when(recipeRepository.findByRecipeType(Mockito.anyString())).thenReturn(getRecipeForVegRecipeType());
        Mockito.when(recipeRepository.findByRecipeServingCount(Mockito.anyInt())).thenReturn(getRecipeForVegRecipeType());
        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(generateSingleRecipeForMock(1L));
    }


    private Optional<Recipe> generateSingleOptionalRecipeForMock(long l)
    {

        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("chicken , pasta");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeInstructions("cook in oven ");
        recipe.setRecipeServingCount(3);
        recipe.setId(1L);
        return Optional.of(recipe);

    }


    private static void prepareRecipeData()
    {
        prepareVegRecipeData();
        prepareNonVegRecipeData();
        for (Recipe vegRec : vegRecipeList)
        {
            expectedVegRecipe = vegRec;
        }
        for (Recipe nonVegRec : nonVegRecipeList)
        {
            expectedNonVegRecipe = nonVegRec;
        }

        allRecipe.add(expectedNonVegRecipe);
        allRecipe.add(expectedVegRecipe);

        // ingredients.setRecipeIngredients(recipeIngredients);

    }


    @Test
    public void whenFindALL_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "", "", "", 0);

        assertEquals(mockedRecipeList.size(), actualDataPresent);

    }
    @Test
    public void whenFindByRecipeTypeVeg_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "", "veg", "", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getRecipeType(), vegRecipeType);

        }

    }

    @Test
    public void whenFindByRecipeTypeVegandServingCount_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "", "veg", "", 3);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getRecipeType(), vegRecipeType);
            assertEquals(mockedRecipe.getRecipeServingCount(), recipeServingCount);

        }

    }
    
    @Test
    public void whenFindrecipeByRecipeServingCount_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "", "", "", 4);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getRecipeServingCount(), recipeServingCount);

        }

    }


    @Test
    public void whenFindrecipeByincludeIngredients_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("chicken", "", "", "", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);

        }
    }


    @Test
    public void whenFindrecipeByincludeIngredientsandServingtype_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("chicken", "", "non-veg", "", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);
            assertEquals(mockedRecipe.getRecipeType(), nonVegRecipeType);

        }
    }
    @Test
    public void whenFindrecipeByincludeIngredientsandServingCount_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("chicken", "", "", "", 3);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);
            assertEquals(mockedRecipe.getRecipeServingCount(), recipeServingCount);

        }
    }
    
    @Test
    public void whenFindrecipeByrecipeInstructionandtypes_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "", "non-veg", "oven", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getRecipeInstructions(), recipeInstructions);
            assertEquals(mockedRecipe.getRecipeType(), nonVegRecipeType);

        }
    }
    
    @Test
    public void whenFindByrecipeInstruction_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "", "", "oven", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getRecipeInstructions(), recipeInstructions);

        }
    }
    
    @Test
    public void whenFindrecipeByExcludeIngredientsandServingtype_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "chicken", "non-veg", "", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);
            assertEquals(mockedRecipe.getRecipeType(), nonVegRecipeType);

        }
    }

    @Test
    public void whenFindrecipeByExcludeIngredientsandRecipeInstructions_thenCorrect() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "chicken", "", "oven", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);
            assertEquals(mockedRecipe.getRecipeInstructions(), recipeInstructions);

        }
    }

    @Test
    public void whenFindrecipeByIncludeIngredients_thenNegative() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("Cabbage", "", "", "", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertNotEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);

        }
    }
    @Test
    public void whenFindrecipeByExcludeIngredients_thenPositive() throws ResourceNotFoundException
    {
        Mockito.when(recipeRepository.findAll()).thenReturn(allRecipe);

        List<Recipe> mockedRecipeList = recipeService.find("", "chicken", "", "", 0);
        for (Recipe mockedRecipe : mockedRecipeList)
        {
            assertNotEquals(mockedRecipe.getIngredients().getRecipeIngredients(), nonVegRecipeIngredients);

        }
    }


//
//    //Tests mainly Covering the Service Layer
//    @Test
//    public void whenFindrecipeByRecipeServingCount_thenCorrect() throws ResourceNotFoundException
//    {
//
//        List<Recipe> mockedRecipeList = recipeRepository.findByRecipeServingCount(recipeServingCount);
//        for (Recipe mockedRecipe : mockedRecipeList)
//        {
//
//            assertEquals(mockedRecipe.getRecipeServingCount(), recipeServingCount);
//        }
//
//    }


    @Test
    public void whenFindrecipeByNonExistingId()
    {

        Long recipeId = 2l;

        assertNotEquals(recipeService.findbyRecipeId(recipeId), recipeId);

    }


    public void whenFindrecipeByNonExistingId_thenException()
    {

        Long recipeId = 2l;
        Mockito.doThrow(new ResourceNotFoundException("No data found ")).when(recipeRepository).findById(recipeId);
    }


    @Test
    public void whenSaverecipe_thenCorrectCreatedId() throws ConstraintsViolationException
    {
        Recipe recipeCreated = null;
        for (Recipe recipe : generateRecipeForMock(1l))
        {
            recipeCreated = recipeService.addNewRecipe(recipe);
        }
        assertEquals(recipeCreated.getId(), 1l);
    }

    @Test
    public void whenUpdateRecipe_thenCorrectCreatedId() throws ConstraintsViolationException
    {
        Recipe recipeCreated = null;
        for (Recipe recipe : generateRecipeForMock(1l))
        {
            recipeCreated = recipeService.updateRecipe(1L,recipe);
        }
        assertEquals(recipeCreated.getId(), 1l);
    }
//    @Test
//    public void whenDeleteRecipe_thenCorrectCreatedId() throws ConstraintsViolationException
//    {
//        Recipe recipeCreated = null;
//        for (Recipe recipe : generateRecipeForMock(1l))
//        {
//            assert(recipeService.deleteRecipe(1L));
//        }
//       
//    }


    //DAO Data
    private List<Recipe> generateRecipeForMock(long id)
    {
        List<Recipe> recipeList = new ArrayList<>();

        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("chicken,pasta");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeServingCount(4);
        recipe.setRecipeInstructions("cook in oven ");
        recipe.setId(1L);
        recipeList.add(recipe);
        return recipeList;
    }


    private Recipe generateSingleRecipeForMock(long id)
    {

        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("chicken , pasta");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeInstructions("cook in oven ");
        recipe.setRecipeServingCount(3);
        recipe.setId(1L);
        return recipe;
    }


    private void setUpSaveException()
    {
        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("chicken,pasta");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("non-veg");
        recipe.setRecipeName("Chicken pasta");
        recipe.setRecipeInstructions("cook in oven ");
        recipe.setRecipeServingCount(4);
        recipe.setId(1L);
        Mockito.doThrow(new DataIntegrityViolationException("Invalid Data")).when(recipeRepository).save(recipe);
    }


    private List<Recipe> getRecipeForVegRecipeType()
    {

        Recipe recipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("matar,paneer");
        recipe.setIngredients(ingredient);
        recipe.setRecipeType("veg");
        recipe.setRecipeName("Matar Paneer");
        recipe.setRecipeServingCount(3);
        recipe.setId(1L);
        vegRecipeList.add(recipe);
        return vegRecipeList;
    }


    private static List<Recipe> prepareNonVegRecipeData()

    {
        Recipe nonVegRecipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("chicken,pasta");
        nonVegRecipe.setIngredients(ingredient);
        nonVegRecipe.setRecipeType("non-veg");
        nonVegRecipe.setRecipeName("Chicken Pasta");
        nonVegRecipe.setRecipeInstructions("cook in oven");
        nonVegRecipe.setRecipeServingCount(3);
        nonVegRecipe.setId(1L);
        nonVegRecipeList.add(nonVegRecipe);
        return nonVegRecipeList;

    }


    private static List<Recipe> prepareVegRecipeData()
    {

        Recipe vegRecipe = new Recipe();
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        ingredient.setRecipeIngredients("matar,paneer");
        vegRecipe.setIngredients(ingredient);
        vegRecipe.setRecipeType("veg");
        vegRecipe.setRecipeName("Matar Paneer");
        vegRecipe.setRecipeServingCount(3);
        vegRecipe.setRecipeInstructions("cook on stove");
        vegRecipe.setId(1L);
        vegRecipeList.add(vegRecipe);
        return vegRecipeList;

    }

}
