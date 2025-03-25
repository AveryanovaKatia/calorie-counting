package ru.project.calorie_counting.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    @NotBlank
    private String name;

    @Positive
    private double caloriesCount;

    @Positive
    private double proteins;

    @Positive
    private double fats;

    @Positive
    private double carbs;
}
