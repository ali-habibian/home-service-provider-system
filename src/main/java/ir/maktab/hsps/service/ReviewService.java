package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Review;
import ir.maktab.hsps.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ReviewService extends BaseService<Review, Long> {
    private final ReviewRepository reviewRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(reviewRepository);
    }
}
