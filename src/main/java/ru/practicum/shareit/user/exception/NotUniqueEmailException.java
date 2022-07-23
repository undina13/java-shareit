package ru.practicum.shareit.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}
