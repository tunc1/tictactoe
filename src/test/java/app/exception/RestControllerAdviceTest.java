package app.exception;

import app.response.ExceptionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

class RestControllerAdviceTest
{
    RestControllerAdvice restControllerAdvice;
    @BeforeEach
    void setUp()
    {
        restControllerAdvice=new RestControllerAdvice();
    }
    @Test
    void entityNotFoundExceptionHandler()
    {
        EntityNotFoundException entityNotFoundException=new EntityNotFoundException();
        ExceptionResponse exceptionResponse=restControllerAdvice.entityNotFoundExceptionHandler(entityNotFoundException);
        Assertions.assertEquals("No Entity Found",exceptionResponse.getMessage());
    }
    @Test
    void conflictExceptionHandler()
    {
        ConflictException conflictException=new ConflictException("Conflict");
        ExceptionResponse exceptionResponse=restControllerAdvice.conflictExceptionHandler(conflictException);
        Assertions.assertEquals(conflictException.getMessage(),exceptionResponse.getMessage());
    }
}