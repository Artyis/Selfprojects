package ru.gor.app.models.dto.genreDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.gor.app.models.entity.Genre;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class GenreListDto {
    private List<Genre> genreList;
}
