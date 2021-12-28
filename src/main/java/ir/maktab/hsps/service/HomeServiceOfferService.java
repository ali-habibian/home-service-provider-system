package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.repository.HomeServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class HomeServiceOfferService extends BaseService<HomeServiceOffer, Long> {
    private final HomeServiceOfferRepository homeServiceOfferRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(homeServiceOfferRepository);
    }
}
