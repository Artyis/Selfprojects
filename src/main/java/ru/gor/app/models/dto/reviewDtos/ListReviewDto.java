package ru.gor.app.models.dto.reviewDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListReviewDto {
    private List<ReviewDto> reviewList;
}
