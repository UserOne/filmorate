package ru.yandex.practicum.filmorate.validation;

public class ValidationException extends RuntimeException {
    ValidationException(String exception) {
        super(exception);
    }
}
