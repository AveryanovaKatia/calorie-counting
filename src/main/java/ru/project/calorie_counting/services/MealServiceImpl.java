package ru.project.calorie_counting.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.calorie_counting.dtos.MealRequestDto;
import ru.project.calorie_counting.dtos.MealResponseDto;
import ru.project.calorie_counting.exceptions.NotFoundException;
import ru.project.calorie_counting.mappers.MealMapper;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.model.Meal;
import ru.project.calorie_counting.model.User;
import ru.project.calorie_counting.repositories.DishRepository;
import ru.project.calorie_counting.repositories.MealRepository;
import ru.project.calorie_counting.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Override
    public MealResponseDto save(MealRequestDto mealRequestDto) {

        log.info("Запрос на добавление нового приема пищи");

        final User user = userRepository.findById(mealRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + mealRequestDto.getUserId()));
        final List<Dish> dishes = dishRepository.findAllById(mealRequestDto.getDishesId());

        final Meal meal = MealMapper.toMeal(mealRequestDto, user, dishes);

        final Meal saveMeal = mealRepository.save(meal);
        log.info("Прием пищи успешно добавлен под id {}", saveMeal.getId());

        return MealMapper.toMealResponseDto(saveMeal);
    }
}
