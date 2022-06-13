package ru.gor.app.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gor.app.exception.NotFoundById;
import ru.gor.app.models.dto.criticDtos.CreateCriticDto;
import ru.gor.app.models.entity.Critic;
import ru.gor.app.repository.CriticRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CriticServiceTest {
    @Mock
    private CriticRepository repos;

    private CriticService service;

    private static Critic critic;

    @BeforeClass
    public static void prepareTestData() {
        critic = Critic
                .builder()
                .id(45)
                .name("Петюня")
                .surname("Валенков")
                .info("+7965558488")
                .reviewList(new ArrayList<>())
                .build();
    }

    @Before
    public void init() {
        service = new CriticService(repos);
    }

    @Test
    public void saveNewCriticTest() throws NotFoundById {
        Critic critic = Critic.builder()
                .name("Иван")
                .surname("Фарт")
                .info("Кто-то")
                .reviewList(new ArrayList<>())
                .build();

        CreateCriticDto criticCreateDto = CreateCriticDto.builder()
                .name(critic.getName())
                .surname(critic.getSurname())
                .info(critic.getInfo())
                .build();

        service.save(criticCreateDto);
        verify(repos).save(any());

    }

    @Test
    public void getAllCriticDto() {
    }

    @Test
    public void getCriticDto() {
        when(repos.getReferenceById(any(Integer.class))).thenReturn(critic);

        CreateCriticDto createDto = service.getCriticDto(critic.getId());

        assertEquals("Name not equal", createDto.getName(), critic.getName());
        assertEquals("Surname not equal", createDto.getSurname(), critic.getSurname());
        assertEquals("Information not equal", createDto.getInfo(), critic.getInfo());
    }

    @Test
    public void editCritic() {
        when(repos.getReferenceById(any(Integer.class))).thenReturn(critic);
        when(repos.save(Mockito.any(Critic.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Critic criticForUpdate = Critic.builder()
                .name("Иван")
                .surname("Фарт")
                .info("Кто-то")
                .reviewList(new ArrayList<>())
                .build();
        CreateCriticDto editDto = CreateCriticDto.builder()
                .name(criticForUpdate.getName())
                .surname(critic.getSurname())
                .info(critic.getInfo())
                .build();

        service.editCritic(editDto, critic.getId());
        Critic resultCritic = repos.getReferenceById(critic.getId());

        assertNotNull(resultCritic);
        assertSame(resultCritic.getId(), critic.getId());
        assertEquals("Name not equal", resultCritic.getName(), critic.getName());
        assertEquals("Surname not equal", resultCritic.getSurname(), critic.getSurname());
        assertEquals("Information not equal", resultCritic.getInfo(), critic.getInfo());
    }

    @Test
    public void deleteCritic() {
        when(repos.getReferenceById(any(Integer.class))).thenReturn(critic);

        service.deleteCritic(critic.getId());
        verify(repos).delete(critic);
    }

}
