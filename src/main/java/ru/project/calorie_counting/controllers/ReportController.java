package ru.project.calorie_counting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.project.calorie_counting.dtos.CalorieCheck;
import ru.project.calorie_counting.dtos.DailyReport;
import ru.project.calorie_counting.dtos.NutritionHistory;
import ru.project.calorie_counting.services.ReportService;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@Validated
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Отчет за день с суммой всех калорий и приемов пищи
    @GetMapping("/daily?userId={userId}&date={date}")
    public ResponseEntity<DailyReport> getDailyReport(
                                   @RequestParam Long userId,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getDailyReport(userId, date));
    }

    // Проверка, уложился ли пользователь в свою дневную норму калорий
    @GetMapping("/check-calories")
    public ResponseEntity<CalorieCheck> checkCalories(
                                    @RequestParam Long userId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.checkCalories(userId, date));
    }

    // История питания по дням
    @GetMapping("/history")
    public ResponseEntity<NutritionHistory> getNutritionHistory(
                                    @RequestParam Long userId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getNutritionHistory(userId, startDate, endDate));
    }
}
