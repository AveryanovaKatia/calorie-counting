package ru.project.calorie_counting.mappers;

import org.springframework.stereotype.Component;
import ru.project.calorie_counting.dtos.UserRequestDto;
import ru.project.calorie_counting.dtos.UserResponseDto;
import ru.project.calorie_counting.model.User;

@Component
public class UserMapper {

    public static User toUser(UserRequestDto userRequestDto) {

        final User user = new User();

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setAge(userRequestDto.getAge());
        user.setGender(userRequestDto.getGender());
        user.setWeight(userRequestDto.getWeight());
        user.setHeight(userRequestDto.getHeight());
        user.setGoal(userRequestDto.getGoal());

        return user;

    }

    public static UserResponseDto toUserResponseDto(User user) {

        final UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setGoal(user.getGoal());
        userResponseDto.setDailyCalorieNorm(user.getDailyCalorieNorm());

        return userResponseDto;
    }
}


