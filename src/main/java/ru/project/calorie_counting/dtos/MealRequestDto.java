package ru.project.calorie_counting.dtos;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealRequestDto {

    @Positive
    private Long userId;

    private List<Long> dishesId;

}
