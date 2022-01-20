package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.offer.HomeServiceOfferCreateParam;
import ir.maktab.hsps.api.offer.HomeServiceOfferCreateResult;
import ir.maktab.hsps.api.offer.HomeServiceOfferModel;
import ir.maktab.hsps.service.HomeServiceOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offers")
public class HomeServiceOfferController {

    private final HomeServiceOfferService offerService;

    @PostMapping
    public ResponseEntity<HomeServiceOfferCreateResult> sendOffer(@RequestBody HomeServiceOfferCreateParam createParam) {
        HomeServiceOfferCreateResult homeServiceOfferCreateResult = offerService.sendOffer(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(homeServiceOfferCreateResult);
    }

    //    http://localhost:8080/offers/filter?orderId={orderId}
    @GetMapping("/filter")
    public ResponseEntity<List<HomeServiceOfferModel>> getByOrderIdAsc(@RequestParam long orderId) {
        List<HomeServiceOfferModel> homeServiceOfferModels = offerService.loadByOrderIdSortAsc(orderId);
        return ResponseEntity.ok(homeServiceOfferModels);
    }

}
