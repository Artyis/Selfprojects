package ru.gor.app.models.dto.reviewDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDto {
    @NotNull(message = "Добавьте дату")
    @PastOrPresent(message = "Минимальная дата - сегодня.")
    private Date date;
    private int film_id;
    private int critic_id;
    @Min(value = 1, message = "Оценка не может быть меньше 1")
    @Max(value = 10, message = "Оценка не может быть больше 10")
    private float score;
    @Size(min = 15, message = "Сделайте более подробное описание")
    private String message;

}
