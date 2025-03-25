package ru.project.calorie_counting.services;

import ru.project.calorie_counting.dtos.MealRequestDto;
import ru.project.calorie_counting.dtos.MealResponseDto;

public interface MealService {

    MealResponseDto save(MealRequestDto mealRequestDto);
}
