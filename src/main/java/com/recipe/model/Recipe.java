package com.recipe.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recipe")
public class Recipe implements Serializable
{

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String recipeName;

    @Column(nullable = false)
    private String recipeType;

    @Column(nullable = false)
    private String recipeInstructions;

    @Column(nullable = false)
    private int recipeServingCount;

    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredients_id", updatable = false)
    private Ingredients ingredients;


    public Ingredients getIngredients()
    {
        return ingredients;
    }


    public void setIngredients(Ingredients ingredients)
    {
        this.ingredients = ingredients;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getRecipeName()
    {
        return recipeName;
    }


    public void setRecipeName(String recipeName)
    {
        this.recipeName = recipeName;
    }


    public String getRecipeType()
    {
        return recipeType;
    }


    public void setRecipeType(String recipeType)
    {
        this.recipeType = recipeType;
    }


    public String getRecipeInstructions()
    {
        return recipeInstructions;
    }


    public void setRecipeInstructions(String recipeInstructions)
    {
        this.recipeInstructions = recipeInstructions;
    }


    public int getRecipeServingCount()
    {
        return recipeServingCount;
    }


    public void setRecipeServingCount(int recipeServingCount)
    {
        this.recipeServingCount = recipeServingCount;
    }

}
