package ru.project.calorie_counting.services;

import ru.project.calorie_counting.dtos.UserRequestDto;
import ru.project.calorie_counting.dtos.UserResponseDto;

public interface UserService {

    UserResponseDto save(UserRequestDto userRequestDto);
}
