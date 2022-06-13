package ru.gor.app.models.dto.genreDtos;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@RequiredArgsConstructor
public class CreateGenreDto {
    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-]+", message = "Используйте только латинские и русские символы")
    @Size(min = 5, max = 50, message = "Название должно содержать от 5 до 50 символов")
    private String name;

    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-.]+", message = "Используйте только латинские и русские символы")
    @Size(min = 5, message = "Добавьте развернутое описание")
    private String description;

}
