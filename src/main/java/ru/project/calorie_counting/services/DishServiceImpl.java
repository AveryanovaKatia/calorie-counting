package ru.project.calorie_counting.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.calorie_counting.dtos.DishDto;
import ru.project.calorie_counting.mappers.DishMapper;
import ru.project.calorie_counting.model.Dish;
import ru.project.calorie_counting.repositories.DishRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Override
    public DishDto save(DishDto dishDto) {

        log.info("Запрос на добавление нового блюда");

        final Dish dish = dishRepository.save(DishMapper.toDish(dishDto));
        log.info("Блюдо успешно добавлено под id {}", dish.getId());

        return dishDto;
    }
}