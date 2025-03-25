package ru.project.calorie_counting.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.calorie_counting.dtos.UserRequestDto;
import ru.project.calorie_counting.dtos.UserResponseDto;
import ru.project.calorie_counting.mappers.UserMapper;
import ru.project.calorie_counting.model.User;
import ru.project.calorie_counting.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {

        log.info("Запрос на добавление пользователя");

        final User user = UserMapper.toUser(userRequestDto);

        log.info("Расчет дневной нормы калорий через формулу Харриса-Бенедикта");
        final double dailyCalorieNorm = calculateDailyCalorieNorm(user);
        user.setDailyCalorieNorm(dailyCalorieNorm);

        final User saveUser = userRepository.save(user);
        log.info("Пользователь успешно добавлен под id {}", user.getId());

        return UserMapper.toUserResponseDto(saveUser);
    }

    private double calculateDailyCalorieNorm(User user) {

        double dailyCalorieNorm = switch (user.getGender()) {
            case MAN -> 88.36 + (13.4 * user.getWeight()) + (4.8 * user.getHeight()) - (5.7 * user.getAge());
            case WOMAN -> 447.6 + (9.2 * user.getWeight()) + (3.1 * user.getHeight()) - (4.3 * user.getAge());
        };

        return switch (user.getGoal()) {
                    case WEIGHT_LOSS -> dailyCalorieNorm * 0.8;
                    case SUPPORT -> dailyCalorieNorm;
                    case MASS_GAIN -> dailyCalorieNorm * 1.2;
                };
    }
}