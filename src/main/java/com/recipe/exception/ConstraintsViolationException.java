package com.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ConstraintsViolationException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public ConstraintsViolationException(String message)
    {
        super(message);
    }

}
