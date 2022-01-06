package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.exception.HomeServiceOfferException;
import ir.maktab.hsps.repository.HomeServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HomeServiceOfferService extends BaseService<HomeServiceOffer, Long> {
    private final HomeServiceOfferRepository homeServiceOfferRepository;
    private final HomeServiceOrderService homeServiceOrderService;

    @PostConstruct
    public void init() {
        setJpaRepository(homeServiceOfferRepository);
    }

    @Transactional
    public HomeServiceOffer sendOffer(HomeServiceOffer homeServiceOffer) {
        Set<SubCategory> proficientSubCategories = homeServiceOffer.getProficient().getSubCategories();
        HomeServiceOrder homeServiceOrder = homeServiceOffer.getHomeServiceOrder();
        SubCategory orderSubCategory = homeServiceOrder.getSubCategory();
        if (!proficientSubCategories.contains(orderSubCategory)) {
            throw new HomeServiceOfferException("The proficient does not have the necessary expertise");
        }

        Double proficientSuggestedPrice = homeServiceOffer.getSuggestedPrice();
        Double orderSuggestedPrice = homeServiceOrder.getSuggestedPrice();
        if (proficientSuggestedPrice < orderSuggestedPrice) {
            throw new HomeServiceOfferException("Proficient suggested price most not be less than order suggested price");
        }

        homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_SELECTION);
        homeServiceOrder.addOffer(homeServiceOffer);
        homeServiceOrderService.update(homeServiceOrder);

        homeServiceOffer.setHomeServiceOrder(homeServiceOrder);

        return super.save(homeServiceOffer);
    }

    public List<HomeServiceOffer> loadByOrderIdSortAsc(long orderId) {
        return homeServiceOfferRepository.findAllByHomeServiceOrder_IdOrderBySuggestedPriceAsc(orderId);
    }
}
