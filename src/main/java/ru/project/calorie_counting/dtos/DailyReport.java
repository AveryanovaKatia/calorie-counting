package ru.project.calorie_counting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReport {

    private LocalDate date;

    private UserResponseDto user;

    private List<MealResponseDto> meals;

    private double totalCalories;
}
