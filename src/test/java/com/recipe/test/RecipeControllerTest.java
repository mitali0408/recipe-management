package com.recipe.test;

import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.recipe.controller.RecipeController;
import com.recipe.model.Ingredients;
import com.recipe.model.Recipe;
import com.recipe.service.RecipeService;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class RecipeControllerTest
{
    private MockMvc mockMvc;

   /* @Autowired
    private WebApplicationContext webApplicationContext;
*/
    @InjectMocks
     RecipeController recipeController;
    @Mock
    private RecipeService recipeServiceMock;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }


    @Test
    public void should_CreateAccount_When_ValidRequest() throws Exception
    {
ResultMatcher ok = MockMvcResultMatchers.status().isCreated();
        Mockito.when(recipeServiceMock.addNewRecipe(Mockito.any(Recipe.class))).thenReturn(prepareRecipeJson());
//        HttpHeaders headers = new HttpHeaders();

      MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
            .post("/recipe/addnewrecipe")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(new Recipe()));
        MvcResult result = mockMvc
            .perform(builder)
            .andExpect(ok).andReturn();
      assertNotNull(result.getResponse().getContentAsString());
      JSONObject actual = new JSONObject(result.getResponse().getContentAsString());
      JSONAssert.assertEquals("{status:FAILED}", actual, false);;
    }


    @Test
    public void test() throws Exception
    {
ResultMatcher ok = MockMvcResultMatchers.status().isCreated();
        Mockito.when(recipeServiceMock.addNewRecipe(Mockito.any(Recipe.class))).thenReturn(prepareRecipeJson());
//        HttpHeaders headers = new HttpHeaders();

      MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
            .post("/recipe/searchrecipe")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(new Recipe()));
        MvcResult result = mockMvc
            .perform(builder)
            .andExpect(ok).andReturn();
      assertNotNull(result.getResponse().getContentAsString());
      JSONObject actual = new JSONObject(result.getResponse().getContentAsString());
      JSONAssert.assertEquals("{status:FAILED}", actual, false);;
    }


    private Recipe prepareRecipeJson()
    {
        Recipe recipe = new Recipe();
        recipe.setRecipeName("chicken pasta");
        recipe.setRecipeInstructions("cook in oven");
        recipe.setRecipeServingCount(4);
        recipe.setRecipeType("non-veg");
        Ingredients ingredient = new Ingredients();
        ingredient.setRecipeIngredients("chicken,pasta");
        recipe.setIngredients(ingredient);
        recipe.setId(1L);
        return recipe;
    }

}
