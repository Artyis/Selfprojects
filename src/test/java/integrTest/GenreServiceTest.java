package integrTest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gor.app.Application;
import ru.gor.app.exception.NotFoundById;
import ru.gor.app.models.dto.genreDtos.CreateGenreDto;
import ru.gor.app.models.dto.genreDtos.GenreListDto;
import ru.gor.app.models.entity.Genre;
import ru.gor.app.repository.GenreRepository;
import ru.gor.app.service.GenreService;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase

public class GenreServiceTest extends Assert {
    @Autowired
    private GenreRepository repository;
    private GenreService genreService;
    private List<Genre> genreList;

    @Before
    public void Setup() throws NotFoundById {
        genreService = new GenreService(repository);
        for (int i = 0; i < 2; i++) {
            CreateGenreDto genreDto = createRandomGenre();
            genreService.save(genreDto);
        }
        genreList = repository.findAll();
    }

    private CreateGenreDto createRandomGenre() {
        int length = new Random().nextInt(4, 25);
        String name = RandomStringUtils.random(length, true, false);
        length = new Random().nextInt(5, 255);
        String description = RandomStringUtils.random(length, true, false);
        return CreateGenreDto.builder()
                .name(name)
                .description(description)
                .build();
    }

    @Test
    public void testDb() {
        CreateGenreDto genreDto = createRandomGenre();
        genreService.save(genreDto);
        GenreListDto genres = genreService.getGenreDto();
        assertEquals(genreList.size() + 1, genres.getGenreList().size());
    }

    @Test
    public void getAllGenreDtoTest() {
        List<Genre> actualList = genreService.getGenreDto().getGenreList();
        assertEquals(genreList.size(), actualList.size());
        for (int i = 0; i < actualList.size(); i++) {
            Genre expected = genreList.get(i);
            Genre actual = actualList.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getDescription(), actual.getDescription());
        }
    }

    @Test
    public void saveNewGenreTest() {
        CreateGenreDto genreDto = createRandomGenre();

        genreService.save(genreDto);
        List<Genre> actualList = genreService.getGenreDto().getGenreList();

        assertEquals("Genre did not Added", genreList.size(), actualList.size(), 1);
        Genre actualGenre = actualList.get(actualList.size() - 1);

        for (Genre genre : genreList) {
            if (genre.equals(actualGenre)) {
                actualGenre = null;
                break;
            }
        }
        assertNotNull("Object not found", actualGenre);

        assertEquals(genreDto.getName(), actualGenre.getName());
        assertEquals(genreDto.getDescription(), actualGenre.getDescription());
    }

    @Test
    public void getGenreByIdTest() {
        int genreIndex = new Random().nextInt(0, genreList.size() - 1);
        Genre genreExpection = genreList.get(genreIndex);
        int genreExpectionId = genreExpection.getId();
        CreateGenreDto dto = genreService.getCreateDto(genreExpectionId);
        Genre genreActual = Genre.builder()
                .id(genreExpection.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        assertEquals(genreExpection.getName(), genreActual.getName());
        assertEquals(genreExpection.getDescription(), genreActual.getDescription());
    }

    @Test(expected = NotFoundById.class)
    public void getOneUser_withNotValidIdTest() {
        int id = 88;
        genreService.getCreateDto(id);
    }

    @Test
    public void editUserTest() {
        CreateGenreDto genreForEdit = createRandomGenre();
        Genre genreOld = genreList.get(new Random().nextInt(0, genreList.size() - 1));
        int genreId = genreOld.getId();
        genreService.editGenre(genreForEdit, genreId);
        CreateGenreDto genreDto = genreService.getCreateDto(genreId);
        assertNotNull("Object not found", genreDto);

        assertEquals(genreForEdit.getName(), genreDto.getName());
        assertEquals(genreForEdit.getDescription(), genreDto.getDescription());
    }

    @Test(expected = NotFoundById.class)
    public void editUser_withNotValidId() {
        CreateGenreDto genreForEdit = createRandomGenre();
        int genreId = 88;
        genreService.editGenre(genreForEdit, genreId);
    }

    @Test
    public void deleteUserTest() {
        Genre genreForDelete = genreList.get(new Random().nextInt(0, genreList.size() - 1));
        int genreId = genreForDelete.getId();

        genreService.deleteGenre(genreId);

        List<Genre> actualList = genreService.getGenreDto().getGenreList();
        assertEquals(actualList.size(), genreList.size(), 1);

        for (Genre user : actualList) {
            assertNotEquals(genreForDelete.getId(), user.getId());
        }
    }

    @Test(expected = NotFoundById.class)
    public void deleteUser_withNotValidIdTest() {
        int id = 88;
        genreService.deleteGenre(id);
    }


}
