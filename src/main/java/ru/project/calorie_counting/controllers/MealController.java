package ru.project.calorie_counting.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.project.calorie_counting.dtos.MealRequestDto;
import ru.project.calorie_counting.dtos.MealResponseDto;
import ru.project.calorie_counting.services.MealService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meal")
@Validated
public class MealController {

    private final MealService mealService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MealResponseDto> save(@RequestBody @Valid MealRequestDto mealRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.save(mealRequestDto));
    }
}
