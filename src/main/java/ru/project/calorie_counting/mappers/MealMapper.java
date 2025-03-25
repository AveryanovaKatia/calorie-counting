package ru.project.calorie_counting.mappers;

import org.springframework.stereotype.Component;
import ru.project.calorie_counting.dtos.MealRequestDto;
import ru.project.calorie_counting.dtos.MealResponseDto;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.model.Meal;
import ru.project.calorie_counting.model.User;

import java.time.LocalDate;
import java.util.List;

@Component
public class MealMapper {

    public static Meal toMeal(MealRequestDto mealRequestDto,
                              User user,
                              List<Dish> dishes) {

        final Meal meal = new Meal();

        meal.setUser(user);
        meal.setDishes(dishes);
        meal.setDate(LocalDate.now());

        return meal;
    }

    public static MealResponseDto toMealResponseDto(Meal meal) {

        final  MealResponseDto mealResponseDto = new MealResponseDto();

        mealResponseDto.setUserResponseDto(UserMapper.toUserResponseDto(meal.getUser()));
        mealResponseDto.setDishes(meal.getDishes().stream().map(DishMapper::toDishDto).toList());
        mealResponseDto.setDate(meal.getDate());

        return mealResponseDto;
    }
}
