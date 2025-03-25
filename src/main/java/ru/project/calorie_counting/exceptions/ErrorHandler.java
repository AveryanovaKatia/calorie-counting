package ru.project.calorie_counting.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.project.calorie_counting.controllers.DishController;
import ru.project.calorie_counting.controllers.MealController;
import ru.project.calorie_counting.controllers.UserController;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class, DishController.class, MealController.class})
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler({ValidationException.class, FileNotFoundException.class, Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValid(final Exception e) {
        log.error("Исключение, {}: {}", e, e.getMessage());
        return Map.of("status", "BAD_REQUEST",
                "reason", "Неправильно составлен запрос",
                "message", e.getMessage(),
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final NotFoundException exception) {
        log.debug("Получен статус 404 NOT_FOUND{}", exception.getMessage(), exception);
        return Map.of("status", "NOT_FOUND",
                "reason", "Объект не найден",
                "message", exception.getMessage(),
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
