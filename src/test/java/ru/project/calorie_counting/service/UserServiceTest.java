package ru.project.calorie_counting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.calorie_counting.dtos.UserRequestDto;
import ru.project.calorie_counting.dtos.UserResponseDto;
import ru.project.calorie_counting.model.User;
import ru.project.calorie_counting.repositories.UserRepository;
import ru.project.calorie_counting.services.UserService;
import ru.project.calorie_counting.services.UserServiceImpl;
import ru.project.calorie_counting.util.Gender;
import ru.project.calorie_counting.util.Goal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUser_Success() {

        UserRequestDto requestDto = new UserRequestDto("Mia", "midnight@mail.ru",
                25, Gender.WOMAN, 60.0, 165.0, Goal.WEIGHT_LOSS);

        User expectedUser = new User(null, "Mia", "midnight@mail.ru", 25,
                Gender.WOMAN, 60.0, 165.0, Goal.WEIGHT_LOSS, 1122.88);

        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        UserResponseDto result = userService.save(requestDto);

        assertNotNull(result);
        assertEquals("Mia", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }
}