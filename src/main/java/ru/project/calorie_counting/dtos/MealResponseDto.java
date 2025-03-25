package ru.project.calorie_counting.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MealResponseDto {

    private UserResponseDto userResponseDto;

    private List<DishDto> dishes;

    private LocalDate date;
}
