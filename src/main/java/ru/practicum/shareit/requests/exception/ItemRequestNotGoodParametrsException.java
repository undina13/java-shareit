package ru.practicum.shareit.requests.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemRequestNotGoodParametrsException extends RuntimeException {
    public ItemRequestNotGoodParametrsException(String message) {
        super(message);
    }
}
