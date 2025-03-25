package ru.project.calorie_counting.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.calorie_counting.dtos.CalorieCheck;
import ru.project.calorie_counting.dtos.DailyReport;
import ru.project.calorie_counting.dtos.DishDto;
import ru.project.calorie_counting.dtos.MealResponseDto;
import ru.project.calorie_counting.dtos.NutritionHistory;
import ru.project.calorie_counting.exceptions.NotFoundException;
import ru.project.calorie_counting.mappers.MealMapper;
import ru.project.calorie_counting.mappers.UserMapper;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.model.Meal;
import ru.project.calorie_counting.model.User;
import ru.project.calorie_counting.repositories.MealRepository;
import ru.project.calorie_counting.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportServiceImpl implements ReportService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public DailyReport getDailyReport(Long userId,
                                      LocalDate date) {

        log.info("Запрос на получение всех приемов пищи за одпределенный день");

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + userId));
        final List<MealResponseDto> meals = mealRepository.findByUserIdAndDate(userId, date)
                .stream().map(MealMapper::toMealResponseDto).toList();
        final double totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToDouble(DishDto::getCaloriesCount)
                .sum();

        log.info("Возвращение всех приемов пищи за день на основании полученны данных");
        return new DailyReport(date, UserMapper.toUserResponseDto(user), meals, totalCalories);
    }

    @Override
    @Transactional(readOnly = true)
    public CalorieCheck checkCalories(Long userId,
                                      LocalDate date) {

        log.info("Запрос на проверку, уложился ли пользователь в свою дневную норму калорий");

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + userId));
        final double dailyNorm = user.getDailyCalorieNorm();
        final double totalCalories = getDailyReport(userId, date).getTotalCalories();
        final boolean isWithinLimit = totalCalories <= dailyNorm;

        if(isWithinLimit) {
            log.info("Пользователь уложился в свою дневную норму калорий");
        } else {
            log.info("Пользователь не уложился в свою дневную норму калорий");
        }
        return new CalorieCheck(UserMapper.toUserResponseDto(user), totalCalories, isWithinLimit);
    }

    @Override
    @Transactional(readOnly = true)
    public NutritionHistory getNutritionHistory(Long userId,
                                                LocalDate startDate,
                                                LocalDate endDate) {

        log.info("Запрос на получение всех приемов пищи пользователя");

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + userId));
        final List<Meal> meals = mealRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        final Map<LocalDate, Double> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(
                        Meal::getDate,
                        Collectors.summingDouble(meal -> meal.getDishes().stream()
                                .mapToDouble(Dish::getCaloriesCount)
                                .sum())
                ));

        return new NutritionHistory(UserMapper.toUserResponseDto(user), caloriesByDay);
    }
}
