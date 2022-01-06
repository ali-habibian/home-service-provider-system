package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.repository.HomeServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class HomeServiceOrderService extends BaseService<HomeServiceOrder, Long> {
    private final HomeServiceOrderRepository homeServiceOrderRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(homeServiceOrderRepository);
    }

    @Transactional
    public HomeServiceOrder acceptOffer(HomeServiceOrder homeServiceOrder) {
        HomeServiceOffer acceptedOffer = homeServiceOrder.getAcceptedOffer();

        acceptedOffer.setIsAccepted(true);

        homeServiceOrder.setAcceptedOffer(acceptedOffer);
        homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_TO_COME);

        return super.update(homeServiceOrder);
    }
}
