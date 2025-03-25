package ru.project.calorie_counting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.calorie_counting.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT m FROM Meal m WHERE m.user.id = :userId AND m.date = :date")
    List<Meal> findByUserIdAndDate(@Param("userId") Long userId,
                                   @Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m WHERE m.user.id = :userId AND m.date BETWEEN :startDate AND :endDate")
    List<Meal> findByUserIdAndDateBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
