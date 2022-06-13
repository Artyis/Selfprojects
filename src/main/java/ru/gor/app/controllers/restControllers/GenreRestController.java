package ru.gor.app.controllers.restControllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import ru.gor.app.models.dto.genreDtos.CreateGenreDto;
import ru.gor.app.models.entity.Genre;
import ru.gor.app.service.GenreService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/genres")
@Api(value = "genre", tags = "API")
public class GenreRestController {
    private GenreService genreService;

    @GetMapping
    public List<Genre> allGenres() {
        return genreService.getGenreDto().getGenreList();
    }

    @GetMapping("/{id}")
    public CreateGenreDto getGenreById(@PathVariable("id") int id) {
        return genreService.getCreateDto(id);
    }

    @PostMapping
    public CreateGenreDto saveNewGenre(@RequestBody @Valid CreateGenreDto createDto) {
        genreService.save(createDto);

        return createDto;
    }

    @PutMapping("/{id}")
    public CreateGenreDto editGenre(@PathVariable int id, @RequestBody @Valid CreateGenreDto genre) {
        genreService.editGenre(genre, id);

        return genre;
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") int id) {
        genreService.deleteGenre(id);
    }

}
