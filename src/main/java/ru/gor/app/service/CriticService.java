package ru.gor.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gor.app.models.dto.criticDtos.CreateCriticDto;
import ru.gor.app.models.dto.criticDtos.CriticsDto;
import ru.gor.app.models.entity.Critic;
import ru.gor.app.repository.CriticRepository;

@RequiredArgsConstructor
@Service
public class CriticService {
    private final CriticRepository criticRepository;

    public void save(CreateCriticDto createDto) {
        Critic critic = getCritic(createDto);
        criticRepository.save(critic);
    }

    public CriticsDto getCriticDto() {
        return new CriticsDto(criticRepository.findAll());
    }

    private Critic getCritic(CreateCriticDto createDto) {
        return Critic.builder()
                .name(createDto.getName())
                .surname(createDto.getSurname())
                .info(createDto.getInfo())
                .build();
    }

    private Critic getCriticById(int id) {
        return criticRepository.getReferenceById(id);
    }

    public CreateCriticDto getCriticDto(int id) {
        Critic critic = getCriticById(id);
        return CreateCriticDto.builder()
                .name(critic.getName())
                .surname(critic.getSurname())
                .info(critic.getInfo())
                .build();
    }

    public void editCritic(CreateCriticDto createDto, int id) {
        Critic critic = getCriticById(id);
        critic.setSurname(createDto.getSurname());
        critic.setName(createDto.getName());
        critic.setInfo(createDto.getInfo());
        criticRepository.save(critic);
    }

    public void deleteCritic(int id) {
        Critic critic = getCriticById(id);
       criticRepository.delete(critic);
    }
}

