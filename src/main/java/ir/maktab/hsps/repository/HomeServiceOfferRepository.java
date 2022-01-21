package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.HomeServiceOffer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeServiceOfferRepository extends JpaRepository<HomeServiceOffer, Long> {
    List<HomeServiceOffer> findAllByHomeServiceOrder_IdOrderBySuggestedPriceAsc(long orderId);

    List<HomeServiceOffer> findAllByProficient_Id(long proficientId);

    @Override
    List<HomeServiceOffer> findAll(Sort sort);
}