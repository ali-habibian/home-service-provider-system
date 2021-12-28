package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
