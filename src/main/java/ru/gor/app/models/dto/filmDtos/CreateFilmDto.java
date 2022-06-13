package ru.gor.app.models.dto.filmDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilmDto {
    @Size(min = 2, max = 30, message = "Необходимая длина названия от 2 до 30 символов")
    private String name;
    @Size(min = 5, message = "Короткое описание")
    private String description;
    @Min(value = 3, message = "Длительность фильма от 3 минут")
    @Max(value = 240, message = "Длительность фильма до 240 минут")
    private int size;
    @Min(value = 1895, message = "Год выхода фильма от 1895 года")
    @Max(value = 2022, message = "Год выхода фильма до 2022 года")
    private int year;
    private List<Integer> genres;

}
