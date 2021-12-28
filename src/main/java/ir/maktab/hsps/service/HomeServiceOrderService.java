package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.repository.HomeServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class HomeServiceOrderService extends BaseService<HomeServiceOrder, Long> {
    private final HomeServiceOrderRepository homeServiceOrderRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(homeServiceOrderRepository);
    }
}
