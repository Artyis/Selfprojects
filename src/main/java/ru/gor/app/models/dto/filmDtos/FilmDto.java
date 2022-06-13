package ru.gor.app.models.dto.filmDtos;

import lombok.*;
import ru.gor.app.models.dto.reviewDtos.ReviewDto;

import java.util.List;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmDto {
    private String name;
    private String description;
    private int time;
    private int release;
    private float score;
    private List<String> genres;
    private List<ReviewDto> reviews;
}
