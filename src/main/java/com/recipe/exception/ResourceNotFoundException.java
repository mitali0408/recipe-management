package com.recipe.exception;

public class ResourceNotFoundException extends RuntimeException
{

    private static final long serialVersionUID = 5344320715962995240L;


    public ResourceNotFoundException(String errorMessage)
    {
        super(errorMessage);

    }
}
