package ru.project.calorie_counting.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.calorie_counting.dtos.CalorieCheck;
import ru.project.calorie_counting.dtos.DailyReport;
import ru.project.calorie_counting.dtos.NutritionHistory;
import ru.project.calorie_counting.exceptions.NotFoundException;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.model.Meal;
import ru.project.calorie_counting.model.User;
import ru.project.calorie_counting.repositories.MealRepository;
import ru.project.calorie_counting.repositories.UserRepository;
import ru.project.calorie_counting.services.ReportService;
import ru.project.calorie_counting.services.ReportServiceImpl;
import ru.project.calorie_counting.util.Gender;
import ru.project.calorie_counting.util.Goal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private MealRepository mealRepository;
    @Mock
    private UserRepository userRepository;

    private ReportService reportService;

    private final Long userId = 1L;
    private final LocalDate today = LocalDate.now();
    private final LocalDate startDate = today.minusDays(7);
    private final LocalDate endDate = today;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl(mealRepository, userRepository);
    }

    @Test
    void getDailyReport_Success() {

        User user = new User(userId, "Anna", "anna@mail.com", 30,
                Gender.WOMAN, 65.0, 170.0, Goal.SUPPORT, 2000.0);

        Dish dish1 = new Dish(1L, "Salad", 150.0, 5.0, 10.0, 3.0);
        Dish dish2 = new Dish(2L, "Chicken", 250.0, 30.0, 15.0, 0.0);

        Meal meal = new Meal(1L, user, List.of(dish1, dish2), today);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndDate(userId, today))
                .thenReturn(List.of(meal));

        DailyReport report = reportService.getDailyReport(userId, today);

        assertNotNull(report);
        assertEquals(today, report.getDate());
        assertEquals(userId, report.getUser().getId());
        assertEquals(1, report.getMeals().size());
        assertEquals(400.0, report.getTotalCalories(), 0.01);

        verify(userRepository).findById(userId);
        verify(mealRepository).findByUserIdAndDate(userId, today);
    }

    @Test
    void getDailyReport_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reportService.getDailyReport(userId, today));

        verify(userRepository).findById(userId);
        verifyNoInteractions(mealRepository);
    }

    @Test
    void checkCalories_WithinLimit() {
        User user = new User(userId, "Anna", "anna@example.com", 30,
                Gender.WOMAN, 65.0, 170.0, Goal.SUPPORT, 2000.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndDate(userId, today))
                .thenReturn(List.of(createMealWithCalories(user, 1500.0)));

        CalorieCheck check = reportService.checkCalories(userId, today);

        assertTrue(check.isWithinLimit());
        assertEquals(1500.0, check.getTotalCalories(), 0.01);
    }

    @Test
    void checkCalories_ExceededLimit() {
        User user = new User(userId, "Anna", "anna@mail.com", 30,
                Gender.WOMAN, 65.0, 170.0, Goal.SUPPORT, 2000.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndDate(userId, today))
                .thenReturn(List.of(createMealWithCalories(user, 2500.0)));

        CalorieCheck check = reportService.checkCalories(userId, today);

        assertFalse(check.isWithinLimit());
        assertEquals(2500.0, check.getTotalCalories(), 0.01);
    }

    @Test
    void getNutritionHistory_Success() {
        User user = new User(userId, "Anna", "anna@mail.com", 30,
                Gender.WOMAN, 65.0, 170.0, Goal.SUPPORT, 2000.0);

        List<Meal> meals = List.of(
                createMealWithDateAndCalories(user, today.minusDays(1), 1500.0),
                createMealWithDateAndCalories(user, today, 1800.0)
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndDateBetween(userId, startDate, endDate))
                .thenReturn(meals);

        NutritionHistory history = reportService.getNutritionHistory(userId, startDate, endDate);

        assertNotNull(history);
        assertEquals(2, history.getCaloriesByDay().size());
        assertEquals(1500.0, history.getCaloriesByDay().get(today.minusDays(1)), 0.01);
        assertEquals(1800.0, history.getCaloriesByDay().get(today), 0.01);
    }

    private Meal createMealWithCalories(User user, double calories) {
        Dish dish = new Dish();
        dish.setCaloriesCount(calories);

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setDate(today);
        meal.setDishes(List.of(dish));

        return meal;
    }

    private Meal createMealWithDateAndCalories(User user, LocalDate date, double calories) {
        Dish dish = new Dish();
        dish.setCaloriesCount(calories);

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setDate(date);
        meal.setDishes(List.of(dish));

        return meal;
    }
}