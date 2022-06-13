package ru.gor.app.models.dto.filmDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmListDto {
    private List<FilmDto> filmDtoList;
}
