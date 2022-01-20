package ir.maktab.hsps.service;

import ir.maktab.hsps.api.offer.HomeServiceOfferCreateParam;
import ir.maktab.hsps.api.offer.HomeServiceOfferCreateResult;
import ir.maktab.hsps.api.offer.HomeServiceOfferModel;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.exception.HomeServiceOfferException;
import ir.maktab.hsps.repository.HomeServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HomeServiceOfferService extends BaseService<HomeServiceOffer, Long> {
    private final HomeServiceOfferRepository homeServiceOfferRepository;
    private final HomeServiceOrderService homeServiceOrderService;
    private final ProficientService proficientService;

    @PostConstruct
    public void init() {
        setJpaRepository(homeServiceOfferRepository);
    }

    @Transactional
    public HomeServiceOfferCreateResult sendOffer(HomeServiceOfferCreateParam createParam) {
        Proficient proficient = proficientService.loadById(createParam.getProficientId());
        Set<SubCategory> proficientSubCategories = proficient.getSubCategories();

        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(createParam.getOrderId());

        // Compare proficient and order category
        SubCategory orderSubCategory = homeServiceOrder.getSubCategory();
        if (!proficientSubCategories.contains(orderSubCategory)) {
            throw new HomeServiceOfferException("The proficient does not have the necessary expertise");
        }

        // Compare proficient SuggestedPrice and order SuggestedPrice
        Double proficientSuggestedPrice = createParam.getSuggestedPrice();
        Double orderSuggestedPrice = homeServiceOrder.getSuggestedPrice();
        if (proficientSuggestedPrice < orderSuggestedPrice) {
            throw new HomeServiceOfferException("Proficient suggested price most not be less than order suggested price");
        }

        HomeServiceOffer homeServiceOffer = createParam.convert2HomeServiceOffer(homeServiceOrder, proficient);

        homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_SELECTION);
        homeServiceOrder.addOffer(homeServiceOffer);

        homeServiceOffer.setHomeServiceOrder(homeServiceOrder);
        HomeServiceOffer saveResult = homeServiceOfferRepository.save(homeServiceOffer);
        return HomeServiceOfferCreateResult.builder()
                .homeServiceOfferId(saveResult.getId())
                .build();
    }

    public List<HomeServiceOfferModel> loadByOrderIdSortAsc(long orderId) {
        // TODO specification
        List<HomeServiceOffer> homeServiceOffers = homeServiceOfferRepository
                .findAllByHomeServiceOrder_IdOrderBySuggestedPriceAsc(orderId);

        List<HomeServiceOfferModel> homeServiceOfferModels = new ArrayList<>();
        homeServiceOffers.forEach(o -> homeServiceOfferModels.add(new HomeServiceOfferModel().convertOffer2Model(o)));
        return homeServiceOfferModels;
    }
}
