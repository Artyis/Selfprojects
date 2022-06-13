package ru.gor.app.controllers.restControllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import ru.gor.app.models.dto.reviewDtos.CreateReviewDto;
import ru.gor.app.models.dto.reviewDtos.ReviewDto;
import ru.gor.app.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@Api(value = "review", tags = "API")
public class ReviewRestController {
    private ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto> allReview(@RequestParam(name = "film", required = false) Integer movie) {
        if(movie != null){
            return reviewService.getListReviewsDto().getReviewList();
        }else {
            return reviewService.getListReviewsDto().getReviewList();
        }
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable("id") int id) {
        return reviewService.getReviewDto(id);
    }

    @PostMapping
    public CreateReviewDto saveNewReview(@RequestBody @Valid CreateReviewDto createDto) {
        reviewService.saveReview(createDto);

        return createDto;
    }

    @PutMapping("/{id}")
    public CreateReviewDto editReview(@PathVariable int id, @RequestBody @Valid CreateReviewDto editReviewDto) {
        reviewService.editReview(editReviewDto, id);

        return editReviewDto;
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") int id) {
        reviewService.deleteReview(id);
    }

}
