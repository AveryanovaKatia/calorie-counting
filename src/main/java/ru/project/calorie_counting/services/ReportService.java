package ru.project.calorie_counting.services;

import ru.project.calorie_counting.dtos.CalorieCheck;
import ru.project.calorie_counting.dtos.DailyReport;
import ru.project.calorie_counting.dtos.NutritionHistory;

import java.time.LocalDate;

public interface ReportService {

    DailyReport getDailyReport(Long userId,
                               LocalDate date);

    CalorieCheck checkCalories(Long userId,
                               LocalDate date);

    NutritionHistory getNutritionHistory(Long userId,
                                         LocalDate startDate,
                                         LocalDate endDate);
}
