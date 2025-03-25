package ru.project.calorie_counting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.calorie_counting.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
