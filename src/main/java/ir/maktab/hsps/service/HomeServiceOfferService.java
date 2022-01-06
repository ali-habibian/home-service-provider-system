package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.exception.HomeServiceOfferException;
import ir.maktab.hsps.repository.HomeServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HomeServiceOfferService extends BaseService<HomeServiceOffer, Long> {
    private final HomeServiceOfferRepository homeServiceOfferRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(homeServiceOfferRepository);
    }

    public HomeServiceOffer sendOffer(HomeServiceOffer homeServiceOffer) {
        Set<SubCategory> proficientSubCategories = homeServiceOffer.getProficient().getSubCategories();
        SubCategory orderSubCategory = homeServiceOffer.getHomeServiceOrder().getSubCategory();
        if (!proficientSubCategories.contains(orderSubCategory)) {
            throw new HomeServiceOfferException("The proficient does not have the necessary expertise");
        }

        Double proficientSuggestedPrice = homeServiceOffer.getSuggestedPrice();
        Double orderSuggestedPrice = homeServiceOffer.getHomeServiceOrder().getSuggestedPrice();
        if (proficientSuggestedPrice < orderSuggestedPrice) {
            throw new HomeServiceOfferException("Proficient suggested price most not be less than order suggested price");
        }

        return super.save(homeServiceOffer);
    }
}
