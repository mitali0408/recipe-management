package com.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.model.Ingredients;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Integer>
{

}
