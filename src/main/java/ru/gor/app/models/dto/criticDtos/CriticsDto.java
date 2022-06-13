package ru.gor.app.models.dto.criticDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gor.app.models.entity.Critic;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticsDto {
    private List<Critic> criticList;
}
