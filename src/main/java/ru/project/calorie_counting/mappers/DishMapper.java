package ru.project.calorie_counting.mappers;

import org.springframework.stereotype.Component;
import ru.project.calorie_counting.dtos.DishDto;
import ru.project.calorie_counting.model.Dish;

@Component
public class DishMapper {

    public static Dish toDish(DishDto dishDto) {

        final Dish dish = new Dish();

        dish.setName(dishDto.getName());
        dish.setCaloriesCount(dishDto.getCaloriesCount());
        dish.setProteins(dishDto.getProteins());
        dish.setFats(dishDto.getFats());
        dish.setCarbs(dishDto.getCarbs());

        return dish;
    }

    public static DishDto toDishDto(Dish dish) {

        final DishDto dishDto = new DishDto();

        dishDto.setName(dish.getName());
        dishDto.setCaloriesCount(dish.getCaloriesCount());
        dishDto.setProteins(dish.getProteins());
        dishDto.setFats(dish.getFats());
        dishDto.setCarbs(dish.getCarbs());

        return dishDto;
    }
}
