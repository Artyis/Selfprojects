package ru.gor.app.models.dto.criticDtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class CreateCriticDto {
    @Size(min = 2, max = 20, message = "Необходимая длина имени от 2 до 20 символов")
    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-]+", message = "Используйте только латинские и русские символы")
    private String name;

    @Size(min = 2, max = 20, message = "Необходимая длина фамилии от 2 до 20 символов")
    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-]+", message = "Используйте только латинские и русские символы")
    private String surname;

    @Size(min = 5, message = "Добавьте информацию о критике")
    private String info;

}
