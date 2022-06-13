package ru.gor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gor.app.models.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}

