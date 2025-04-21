package com.example.todoapp_remake.exception.handler;

import com.example.todoapp_remake.dto.response.ErrorResponse;
import com.example.todoapp_remake.exception.CannotCreateDependencyBetweenUncommonItemsException;
import com.example.todoapp_remake.exception.CannotMarkItemCompletedBeforeTheDependentItemException;
import com.example.todoapp_remake.exception.DependencyLoopException;
import com.example.todoapp_remake.exception.DependencyNotFoundException;
import com.example.todoapp_remake.exception.ItemAlreadyExistException;
import com.example.todoapp_remake.exception.ItemNotFoundException;
import com.example.todoapp_remake.exception.ListAlreadyExistsException;
import com.example.todoapp_remake.exception.ListNotFoundException;
import com.example.todoapp_remake.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    protected ErrorResponse handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ListNotFoundException.class)
    protected ErrorResponse handleListNotFoundException(ListNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ListAlreadyExistsException.class)
    protected ErrorResponse handleListAlreadyExistsException(ListAlreadyExistsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemAlreadyExistException.class)
    protected ErrorResponse handleItemAlreadyExistException(ItemAlreadyExistException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    protected ErrorResponse handleItemNotFoundException(ItemNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotMarkItemCompletedBeforeTheDependentItemException.class)
    protected ErrorResponse handleCannotMarkItemCompletedBeforeTheDependentItemException(CannotMarkItemCompletedBeforeTheDependentItemException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DependencyNotFoundException.class)
    protected ErrorResponse handleDependencyNotFoundException(DependencyNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DependencyLoopException.class)
    protected ErrorResponse handleDependencyLoopException(DependencyLoopException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotCreateDependencyBetweenUncommonItemsException.class)
    protected ErrorResponse handleCannotCreateDependencyBetweenUncommonItemsException(CannotCreateDependencyBetweenUncommonItemsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
