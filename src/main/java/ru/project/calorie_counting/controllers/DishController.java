package ru.project.calorie_counting.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.project.calorie_counting.dtos.DishDto;
import ru.project.calorie_counting.services.DishService;

@RestController
@RequestMapping("/dish")
@Validated
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DishDto> save(@RequestBody @Valid DishDto dishDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.save(dishDto));
    }

}
