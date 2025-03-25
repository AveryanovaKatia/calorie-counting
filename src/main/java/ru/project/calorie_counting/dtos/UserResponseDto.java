package ru.project.calorie_counting.dtos;

import lombok.Data;
import ru.project.calorie_counting.util.Goal;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private Goal goal;

    private double dailyCalorieNorm;
}
