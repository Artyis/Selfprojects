package ru.gor.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gor.app.exception.NotFoundById;
import ru.gor.app.models.SortFields;
import ru.gor.app.models.dto.filmDtos.CreateFilmDto;
import ru.gor.app.models.dto.filmDtos.FilmDto;
import ru.gor.app.models.dto.filmDtos.FilmListDto;
import ru.gor.app.models.dto.reviewDtos.ReviewDto;
import ru.gor.app.models.entity.Film;
import ru.gor.app.models.entity.Genre;
import ru.gor.app.repository.FilmRepository;
import ru.gor.app.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@RequiredArgsConstructor
@Service
public class FilmService {
    private static final float DEFAULT_SCORE = 5;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final ReviewService reviewService;

    public void saveFilm(CreateFilmDto filmDto){
        Film film = getFilm(filmDto);
        filmRepository.save(film);

    }
    private Film getFilm(CreateFilmDto filmDto) {
        List<Genre> genres = genreRepository.findAllById(filmDto.getGenres());
        return Film.builder()
                .name(filmDto.getName())
                .description(filmDto.getDescription())
                .score(DEFAULT_SCORE)
                .time(filmDto.getSize())
                .release(filmDto.getYear())
                .genres(genres)
                .build();
    }
    private Film getFilmById(int id) {
        return filmRepository.findById(id).orElseThrow(NotFoundById::new);
    }

    public void editFilm(CreateFilmDto createDto, int id) {
        Film film = getFilmById(id);
        film.setName(createDto.getName());
        film.setDescription(createDto.getDescription());
        filmRepository.save(film);
    }

    public void deleteMovie(int id) {
        Film film = getFilmById(id);
        filmRepository.delete(film);
    }
    public FilmListDto getListFilmDto() {
        List<FilmDto> films = new ArrayList<>();
        filmRepository.findAll().forEach(film -> films.add(createNewFilmDto(film)));
        return new FilmListDto(films);
    }

    public FilmDto getLook(int id) {
        Film film = getFilmById(id);
        return createNewFilmDto(film);
    }
    private FilmDto createNewFilmDto(Film film) {
        List<ReviewDto> reviews = new ArrayList<>(film.getReviews().size());
        film.getReviews().forEach(review ->
                reviews.add(reviewService.getReviewDto(review)));
        return FilmDto.builder()
                .name(film.getName())
                .description(film.getDescription())
                .score(film.getScore())
                .time(film.getTime())
                .genres(getListGenres(film.getGenres()))
                .release(film.getRelease())
                .reviews(reviews)
                .build();
    }

    private List<String> getListGenres(List<Genre> genres) {
        List<String> names = new ArrayList<>(genres.size());
        genres.forEach(genre -> names.add(genre.getName()));
        return names;
    }

    public Page<FilmDto> searchFilm(String name,
                                    Integer minYear,
                                    Integer maxYear,
                                    Float minScore,
                                    Float maxScore,
                                    List<Integer> genreIds,
                                    SortFields sort,
                                    Sort.Direction directionSort,
                                    Integer page,
                                    Integer size) {
        Page<Film> film;

        if (sort == null) {
            sort = SortFields.id;
        }
        if (directionSort == null) {
            directionSort = DESC;
        }
        if (size == null) {
            size = 100;
        }
        if (page == null) {
            page = 0;
        }
        if (name == null) {
            name = "";
        }
        if (minScore == null) {
            minScore = 0f;
        }
        if (maxScore == null) {
            maxScore = 10F;
        }
        if (minYear == null) {
            minYear = 1895;
        }
        if (maxYear == null) {
            maxYear = 2022;
        }

        Sort pageSort = by(directionSort, sort.toString());
        PageRequest pageRequest = PageRequest.of(page, size, pageSort);

        if(genreIds == null) {
            film = filmRepository.findByField("%" + name + "%", minYear, maxYear, minScore, maxScore, pageRequest);
        }else {
            film = filmRepository.findByFieldWithGenres("%" + name + "%", minYear, maxYear, minScore, maxScore, genreIds, pageRequest);
        }
        List<FilmDto> filmList = new ArrayList<>(film.getContent().size());
        film.getContent().forEach(films -> filmList.add(createNewFilmDto(films)));

        return new PageImpl<>(filmList, pageRequest, film.getTotalElements());
    }






}
