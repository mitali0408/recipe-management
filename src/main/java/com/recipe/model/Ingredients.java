package com.recipe.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ingredients")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recipe"})
public class Ingredients implements Serializable
{

    @Id
    @SequenceGenerator(name="INGREDIENTS_SEQ",sequenceName="INGREDIENTS_SEQ",initialValue=1,allocationSize=1)
    @GeneratedValue(generator="INGREDIENTS_SEQ")
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String recipeIngredients;


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ingredients")
    private Recipe recipe;


    public Recipe getRecipe()
    {
        return recipe;
    }


    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }


    public String getRecipeIngredients()
    {
        return recipeIngredients;
    }


    public void setRecipeIngredients(String recipeIngredients)
    {
        this.recipeIngredients = recipeIngredients;
    }

}
