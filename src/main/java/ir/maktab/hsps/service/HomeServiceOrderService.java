package ir.maktab.hsps.service;

import ir.maktab.hsps.repository.HomeServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeServiceOrderService {
    private final HomeServiceOrderRepository homeServiceOrderRepository;


}
