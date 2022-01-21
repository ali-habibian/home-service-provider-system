package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.review.ReviewCreateParam;
import ir.maktab.hsps.api.review.ReviewCreateResult;
import ir.maktab.hsps.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewCreateResult> addReview(@RequestBody ReviewCreateParam createParam) {
        ReviewCreateResult result = reviewService.save(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
