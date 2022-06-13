package ru.gor.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gor.app.exception.NotFoundById;
import ru.gor.app.models.dto.genreDtos.CreateGenreDto;
import ru.gor.app.models.dto.genreDtos.GenreListDto;
import ru.gor.app.models.entity.Genre;
import ru.gor.app.repository.GenreRepository;

import java.util.List;
@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepo;

    public GenreListDto getGenreDto() {
        List<Genre> genreList = genreRepo.findAll();
        return new GenreListDto(genreList);
    }

    public void save(CreateGenreDto createDto) {
        Genre genre = getGenre(createDto);
        genreRepo.save(genre);
    }

    private Genre getGenre(CreateGenreDto createDto) {
        return Genre.builder()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .build();
    }

    private Genre getGenreById(int id) {
        return genreRepo.findById(id).orElseThrow(NotFoundById::new);
    }

    public CreateGenreDto getCreateDto(int id) {
        Genre genre = getGenreById(id);
        return CreateGenreDto.builder()
                .name(genre.getName())
                .description(genre.getDescription())
                .build();
    }

    public void editGenre(CreateGenreDto createDto, int id) {
        Genre genre = getGenreById(id);
        genre.setName(createDto.getName());
        genre.setDescription(createDto.getDescription());
        genreRepo.save(genre);
    }

    public void deleteGenre(int id) {
        Genre genre = getGenreById(id);
        genreRepo.delete(genre);
    }

}
