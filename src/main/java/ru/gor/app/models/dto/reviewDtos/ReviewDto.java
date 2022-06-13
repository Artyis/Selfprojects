package ru.gor.app.models.dto.reviewDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String date;
    private String film;
    private String critic;
    private float score;
    private String message;
}
