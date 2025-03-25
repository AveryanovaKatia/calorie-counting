package ru.project.calorie_counting.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.calorie_counting.util.Gender;
import ru.project.calorie_counting.util.Goal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Емейл должен содержать @ и наименование")
    private String email;

    @Positive
    private int age;

    @NotNull
    private Gender gender;

    @Positive
    private double weight;

    @Positive
    private double height;

    @NotNull
    private Goal goal;
}
