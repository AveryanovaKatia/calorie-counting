package ru.project.calorie_counting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalorieCheck {

    private UserResponseDto user;

    private double totalCalories;

    private boolean isWithinLimit;
}
