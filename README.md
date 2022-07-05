# recipe-management

Technology Used :

JDK 1.8
SpringBoot
Spring JPA
REST
MYSQL
Swagger
Junit

Methods	    Urls	                             Actions
POST	  /recipe/createrecipe	             create new Recipe

GET	    /recipe/createrecipe	                 retrieve all Recipes
GET	    /recipe/searchrecipe/{recipeid}	               retrieve a Recipe by recipeid
GET	    /recipe/searchrecipe/?recipeType=veg	   find all Recipes which recipeType  veg

DELETE	/recipe/deleterecipe/{recipeid}	               delete a Recipe by recipeid

PUT   	/recipe/updaterecipe/{recipeid}	               update a Recipe by recipeid


Run Instructions -
Checkout master branch from the repo 
run RecipeManagementApplication.java file.
Swagger is up 
