package com.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find entity with id.")
public class EntityNotFoundException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message)
    {
        super(message);
    }

}
