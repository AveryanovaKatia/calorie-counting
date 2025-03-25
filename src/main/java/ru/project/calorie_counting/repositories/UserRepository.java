package ru.project.calorie_counting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.calorie_counting.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
