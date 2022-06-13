package ru.gor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gor.app.models.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
