package ru.gor.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gor.app.exception.NotFoundById;
import ru.gor.app.models.dto.reviewDtos.CreateReviewDto;
import ru.gor.app.models.dto.reviewDtos.ListReviewDto;
import ru.gor.app.models.dto.reviewDtos.ReviewDto;
import ru.gor.app.models.entity.Critic;
import ru.gor.app.models.entity.Film;
import ru.gor.app.models.entity.Review;
import ru.gor.app.repository.CriticRepository;
import ru.gor.app.repository.FilmRepository;
import ru.gor.app.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ReviewService {
    private ReviewRepository repository;
    private CriticRepository criticRepository;
    private FilmRepository filmRepository;

    public void saveReview (CreateReviewDto createReviewDto){
        Review review = getNewReview(createReviewDto);
        repository.save(review);
        float sum = (float) review.getFilm().getReviews().stream().mapToDouble(Review::getScore).sum() + 5;
        review.getFilm().setScore(sum / (review.getFilm().getReviews().size() + 1));
        filmRepository.save(review.getFilm());
    }
    private Review getNewReview(CreateReviewDto createReviewDto) {
        Critic critic = criticRepository.getReferenceById(createReviewDto.getCritic_id());
        Film film =  filmRepository.findById(createReviewDto.getFilm_id()).orElseThrow(NotFoundById::new);
        return Review.builder()
                .date(createReviewDto.getDate())
                .critic(critic)
                .film(film)
                .message(createReviewDto.getMessage())
                .score(createReviewDto.getScore())
                .build();
    }
    public ReviewDto getReviewDto(int id) {
        Review review =repository.getReferenceById(id);
        return createReviewDto(review);
    }
    public ReviewDto getReviewDto(Review review) {
        return createReviewDto(review);
    }
    private ReviewDto createReviewDto(Review review) {
        return ReviewDto.builder()
                .date(review.getDate().toLocalDate().toString())
                .critic(review.getCritic().getName()+" "+review.getCritic().getSurname())
                .film(review.getFilm().getName())
                .message(review.getMessage())
                .score(review.getScore())
                .build();
    }
    public void editReview(CreateReviewDto createDto, int id) {
        Review review = repository.getReferenceById(id);
        Critic critic = criticRepository.getReferenceById(createDto.getCritic_id());
        Film film =  filmRepository.findById(createDto.getFilm_id()).orElseThrow(NotFoundById::new);
        review.setCritic(critic);
        review.setFilm(film);
        review.setMessage(createDto.getMessage());
        review.setScore(createDto.getScore());
        review.setDate(createDto.getDate());
        repository.save(review);
        float sum = (float) review.getFilm().getReviews().stream().mapToDouble(Review::getScore).sum() + 5;
        review.getFilm().setScore(sum / (review.getFilm().getReviews().size() + 1));
        filmRepository.save(review.getFilm());
    }
    public void deleteReview(int id) {
        Review review = repository.getReferenceById(id);
        repository.delete(review);
    }
    public ListReviewDto getListReviewsDto() {
        List<ReviewDto> reviews = new ArrayList<>();
        repository.findAll().forEach(review -> reviews.add(createReviewDto(review)));
        return new ListReviewDto(reviews);
    }

    public ListReviewDto getListReviewsDto(int movieId) {
        List<ReviewDto> reviews = new ArrayList<>();
        filmRepository.findById(movieId).orElseThrow(NotFoundById::new).getReviews().forEach(review -> reviews.add(createReviewDto(review)));
        return new ListReviewDto(reviews);
    }
}
