package ir.maktab.hsps.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.hsps.api.offer.HomeServiceOfferCreateParam;
import ir.maktab.hsps.api.offer.HomeServiceOfferCreateResult;
import ir.maktab.hsps.api.offer.HomeServiceOfferModel;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.querydsl.customer.CustomerPredicatesBuilder;
import ir.maktab.hsps.querydsl.offer.OfferPredicatesBuilder;
import ir.maktab.hsps.service.HomeServiceOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offers")
public class HomeServiceOfferController {

    private final HomeServiceOfferService offerService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<HomeServiceOfferCreateResult> sendOffer(@RequestBody HomeServiceOfferCreateParam createParam) {
        HomeServiceOfferCreateResult homeServiceOfferCreateResult = offerService.sendOffer(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(homeServiceOfferCreateResult);
    }

    //    http://localhost:8080/offers/filter?orderId={orderId}
    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('offer:read')")
    public ResponseEntity<List<HomeServiceOfferModel>> getByOrderIdAsc(@RequestParam long orderId) {
        List<HomeServiceOfferModel> homeServiceOfferModels = offerService.loadByOrderIdSortAsc(orderId);
        return ResponseEntity.ok(homeServiceOfferModels);
    }

    //    http://localhost:8080/orders/filterByProficientId?proficientId={proficientId}
    @GetMapping("/filterByProficientId")
    @PreAuthorize("hasAuthority('offer:read')")
    public ResponseEntity<List<HomeServiceOfferModel>> getAllByProficientId(@RequestParam long proficientId) {
        List<HomeServiceOfferModel> result = offerService.loadByProficientId(proficientId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('offer:read')")
    public Iterable<HomeServiceOfferModel> findAllByQuerydsl(@RequestParam(value = "search") String search) {
        System.out.println("search = " + search);
        OfferPredicatesBuilder builder = new OfferPredicatesBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return offerService.findAll(exp);
    }

}
