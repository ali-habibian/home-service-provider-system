package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.home_service_order.HomeServiceOrderCreateParam;
import ir.maktab.hsps.api.home_service_order.HomeServiceOrderCreateResult;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.service.HomeServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class HomeServiceOrderController {

    private final HomeServiceOrderService orderService;

    @PostMapping
    public ResponseEntity<HomeServiceOrderCreateResult> addOrder(@RequestBody HomeServiceOrderCreateParam createParam) {
        HomeServiceOrderCreateResult homeServiceOrderCreateResult = orderService.saveHomeServiceOrder(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(homeServiceOrderCreateResult);
    }
}
