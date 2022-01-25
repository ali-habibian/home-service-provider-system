package ir.maktab.hsps.service;

import ir.maktab.hsps.api.review.ReviewCreateParam;
import ir.maktab.hsps.api.review.ReviewCreateResult;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Review;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.exception.ResourceNotFoundException;
import ir.maktab.hsps.exception.ReviewException;
import ir.maktab.hsps.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;

    @Transactional
    public ReviewCreateResult save(ReviewCreateParam createParam) {
        Customer customer = customerService.loadById(createParam.getCustomerId());

        HomeServiceOrder homeServiceOrder = customer.getHomeServiceOrders().stream()
                .filter(o -> o.getId() == createParam.getHomeServiceOrderId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("HomeServiceOrder", "id", createParam.getHomeServiceOrderId()));

        if (OrderStatus.FINISHED != homeServiceOrder.getOrderStatus()){
            throw new ReviewException("Reviews are only sent for orders with finished status");
        }

        HomeServiceOffer acceptedOffer = homeServiceOrder.getAcceptedOffer();
        Proficient proficient = acceptedOffer.getProficient();

        Review review = createParam.convert2Review(customer, proficient, acceptedOffer);
        proficient.addReview(review);

        Review saveResult = reviewRepository.save(review);

        return new ReviewCreateResult(saveResult.getId());
    }
}
