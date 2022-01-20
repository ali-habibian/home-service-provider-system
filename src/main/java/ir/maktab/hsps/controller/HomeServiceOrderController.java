package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.order.HomeServiceOrderCreateParam;
import ir.maktab.hsps.api.order.HomeServiceOrderCreateResult;
import ir.maktab.hsps.api.order.OfferAcceptParam;
import ir.maktab.hsps.api.order.OrderUpdateResult;
import ir.maktab.hsps.service.HomeServiceOfferService;
import ir.maktab.hsps.service.HomeServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderUpdateResult> acceptOffer(@RequestBody OfferAcceptParam offerAcceptParam, @PathVariable long orderId) {
        offerAcceptParam.setOrderId(orderId);
        OrderUpdateResult orderUpdateResult = orderService.acceptOffer(offerAcceptParam);
        return ResponseEntity.ok(orderUpdateResult);
    }

    //    http://localhost:8080/orders/finish?orderId={orderId}
    @PutMapping("/finish")
    public ResponseEntity<OrderUpdateResult> finishOrder(@RequestParam long orderId) {
        OrderUpdateResult orderUpdateResult = orderService.finishOrder(orderId);
        return ResponseEntity.ok(orderUpdateResult);
    }

}
