package ru.project.calorie_counting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.calorie_counting.dtos.MealRequestDto;
import ru.project.calorie_counting.dtos.MealResponseDto;
import ru.project.calorie_counting.exceptions.NotFoundException;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.model.Meal;
import ru.project.calorie_counting.model.User;
import ru.project.calorie_counting.repositories.DishRepository;
import ru.project.calorie_counting.repositories.MealRepository;
import ru.project.calorie_counting.repositories.UserRepository;
import ru.project.calorie_counting.services.MealService;
import ru.project.calorie_counting.services.MealServiceImpl;
import ru.project.calorie_counting.util.Gender;
import ru.project.calorie_counting.util.Goal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DishRepository dishRepository;

    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealServiceImpl(mealRepository, userRepository, dishRepository);
    }

    @Test
    void testCreateMeal_Success() {
        Long userId = 1L;
        List<Long> dishIds = List.of(1L, 2L);
        MealRequestDto mealRequestDto = new MealRequestDto(userId, dishIds);

        User expectedUser = new User(userId, "Mia", "midnight@mail.ru", 25, Gender.WOMAN,
                60.0, 165.0, Goal.WEIGHT_LOSS, 1122.88
        );

        List<Dish> expectedDishes = List.of(
                new Dish(1L, "Pasta", 460.0, 25.0, 50.0, 165.0),
                new Dish(2L, "Pizza", 660.0, 17.0, 80.0, 285.0)
        );

        Meal expectedMeal = new Meal();
        expectedMeal.setId(1L);
        expectedMeal.setUser(expectedUser);
        expectedMeal.setDishes(expectedDishes);
        expectedMeal.setDate(LocalDate.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        when(dishRepository.findAllById(dishIds)).thenReturn(expectedDishes);
        when(mealRepository.save(any(Meal.class))).thenReturn(expectedMeal);

        MealResponseDto result = mealService.save(mealRequestDto);

        assertNotNull(result);
        assertEquals(expectedUser.getId(), result.getUserResponseDto().getId());
        assertEquals(expectedDishes.size(), result.getDishes().size());

        verify(userRepository, times(1)).findById(userId);
        verify(dishRepository, times(1)).findAllById(dishIds);
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void testCreateMeal_UserNotFound() {

        Long userId = 999L;
        MealRequestDto mealRequestDto = new MealRequestDto(userId, List.of(1L));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            mealService.save(mealRequestDto);
        });

        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(dishRepository, mealRepository);
    }
}