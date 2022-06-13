package ru.gor.app.controllers.restControllers;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.gor.app.models.SortFields;
import ru.gor.app.models.dto.filmDtos.CreateFilmDto;
import ru.gor.app.models.dto.filmDtos.FilmDto;
import ru.gor.app.service.FilmService;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/films")
@Api(value = "film", tags = "API")
public class FilmRestController {
    private final FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<FilmDto> allMovie() {
        return filmService.getListFilmDto().getFilmDtoList();
    }


    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable("id") int id) {
        return filmService.getLook(id);
    }

    @PostMapping
    public CreateFilmDto saveNew(@RequestBody @Valid CreateFilmDto createDto) {
        filmService.saveFilm(createDto);

        return createDto;
    }

    @PutMapping("/{id}")
    public CreateFilmDto edit(@PathVariable int id, @RequestBody @Valid CreateFilmDto movieCreateDto) {
        filmService.editFilm(movieCreateDto, id);
        return movieCreateDto;
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") int id) {
        filmService.deleteMovie(id);
    }
    @GetMapping("/search")
    public Page<FilmDto> findMovie(@RequestParam(name = "genre", required = false) List<Integer> genre,
                                   @RequestParam(name = "size", required = false) Integer size,
                                   @RequestParam(name = "page", required = false) Integer page,
                                   @RequestParam(name = "sort", required = false) SortFields sort,
                                   @RequestParam(name = "direction", required = false) Sort.Direction directionSort,
                                   @RequestParam(name = "name", required = false) String name,
                                   @RequestParam(name = "min_year", required = false) Integer minYear,
                                   @RequestParam(name = "max_year", required = false) Integer maxYear,
                                   @RequestParam(name = "min_score", required = false) Float minScore,
                                   @RequestParam(name = "max_score", required = false) Float maxScore) {
        return filmService.searchFilm(name, minYear, maxYear, minScore, maxScore, genre, sort, directionSort, size, page);
    }



}
