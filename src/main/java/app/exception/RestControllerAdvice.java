package app.exception;

import javax.persistence.EntityNotFoundException;

import app.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice
{
    @ExceptionHandler(value={EntityNotFoundException.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ExceptionResponse entityNotFoundExceptionHandler(EntityNotFoundException e)
    {
        return new ExceptionResponse("No Entity Found");
    }
    @ExceptionHandler(value={ConflictException.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ExceptionResponse conflictExceptionHandler(ConflictException e)
    {
        return new ExceptionResponse(e.getMessage());
    }
}