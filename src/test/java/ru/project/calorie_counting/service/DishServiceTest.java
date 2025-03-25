package ru.project.calorie_counting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.calorie_counting.dtos.DishDto;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.repositories.DishRepository;
import ru.project.calorie_counting.services.DishService;
import ru.project.calorie_counting.services.DishServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    private DishService dishService;

    @BeforeEach
    void setUp() {
        dishService =  new DishServiceImpl(dishRepository);
    }

    @Test
    void testCreateDish_Success() {

        DishDto dishDto = new DishDto("Pasta", 460.0,
                25.0, 50.0, 165.0);

        Dish expectedDish = new Dish(1L,"Pasta", 460.0,
                25.0, 50.0, 165.0);

        when(dishRepository.save(any(Dish.class))).thenReturn(expectedDish);

        DishDto result = dishService.save(dishDto);

        assertNotNull(result);
        assertEquals("Pasta", result.getName());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }
}
