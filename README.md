# recipe-management

Technology Used :

JDK 1.8
SpringBoot 2.4.5
Spring JPA
REST
MYSQL
Swagger
Junit


POST -->  /recipe/createrecipe	             create new Recipe

GET	 -->   /recipe/createrecipe	                 retrieve all Recipes
GET	 -->   /recipe/searchrecipe/{recipeid}	               retrieve a Recipe by recipeid
GET	 -->   /recipe/searchrecipe/?recipeType=veg	   find all Recipes which recipeType  veg

DELETE--> -	/recipe/deleterecipe/{recipeid}	               delete a Recipe by recipeid

PUT   -->	/recipe/updaterecipe/{recipeid}	               update a Recipe by recipeid


Run Instructions -
Checkout master branch from the repo 
run query --> create db recipemanagement;
run RecipeManagementApplication.java file. Tables will be automatically generated.
Swagger is up .Data can be inserted with it and crud test also.


Test
