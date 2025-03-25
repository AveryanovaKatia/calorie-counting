package ru.project.calorie_counting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionHistory {

    private UserResponseDto user;

    private Map<LocalDate, Double> caloriesByDay;
}
