package ir.maktab.hsps.service;

import ir.maktab.hsps.repository.HomeServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeServiceOfferService {
    private final HomeServiceOfferRepository homeServiceOfferRepository;


}
