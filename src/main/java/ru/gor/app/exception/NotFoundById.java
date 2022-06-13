package ru.gor.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundById extends RuntimeException{
    public NotFoundById() {
        super("По Id ниче нет");
    }
}

