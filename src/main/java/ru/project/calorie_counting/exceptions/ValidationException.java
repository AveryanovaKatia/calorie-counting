package ru.project.calorie_counting.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}
