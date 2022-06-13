package ru.gor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gor.app.models.entity.Critic;

public interface CriticRepository extends JpaRepository<Critic, Integer> {
}
