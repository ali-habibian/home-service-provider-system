package ir.maktab.hsps.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.hsps.api.order.*;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.repository.HomeServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceOrderService {
    private final HomeServiceOrderRepository homeServiceOrderRepository;
    private final CustomerService customerService;
    private final SubCategoryService subCategoryService;

    @Transactional
    public HomeServiceOrderCreateResult saveHomeServiceOrder(HomeServiceOrderCreateParam createParam) {
        Customer customer = customerService.loadById(createParam.getCustomerId());
        SubCategory subCategory = subCategoryService.loadById(createParam.getSubCategoryId());

        HomeServiceOrder homeServiceOrder = createParam.convert2HomeServiceOrder(customer, subCategory);
        homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_SUGGESTION);

        HomeServiceOrder saveResult = homeServiceOrderRepository.save(homeServiceOrder);
        return HomeServiceOrderCreateResult.builder()
                .homeServiceOrderId(saveResult.getId())
                .build();
    }

    @Transactional
    public OrderUpdateResult acceptOffer(OfferAcceptParam offerAcceptParam) {
        HomeServiceOrder homeServiceOrder = homeServiceOrderRepository.getById(offerAcceptParam.getOrderId());
        homeServiceOrder.getHomeServiceOffers().forEach(o -> {
            if (o.getId() == offerAcceptParam.getAcceptedOfferId()) {
                homeServiceOrder.acceptOffer(o);
                homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_TO_COME);
            }
        });

        HomeServiceOrder result = homeServiceOrderRepository.save(homeServiceOrder);
        return OrderUpdateResult.builder()
                .id(result.getId())
                .success(true)
                .build();
    }

    @Transactional
    public OrderUpdateResult finishOrder(long orderId) {
        HomeServiceOrder homeServiceOrder = homeServiceOrderRepository.getById(orderId);
        homeServiceOrder.setOrderStatus(OrderStatus.FINISHED);
        homeServiceOrder.setOrderFinishedDate(Instant.now());

        HomeServiceOrder result = homeServiceOrderRepository.save(homeServiceOrder);
        return OrderUpdateResult.builder()
                .id(result.getId())
                .success(true)
                .build();
    }

    public HomeServiceOrder loadById(long id) {
        return homeServiceOrderRepository.getById(id);
    }

    public List<HomeServiceOrderModel> findAllByCustomerId(long customerId) {
        List<HomeServiceOrder> orders = homeServiceOrderRepository.findAllByCustomer_Id(customerId);
        List<HomeServiceOrderModel> orderModels = new ArrayList<>();
        orders.forEach(o -> orderModels.add(new HomeServiceOrderModel().convertOrder2Model(o)));
        return orderModels;
    }

    public Iterable<HomeServiceOrderModel> findAll(BooleanExpression exp) {
        List<HomeServiceOrderModel> orderModels = new ArrayList<>();
        homeServiceOrderRepository.findAll(exp).forEach(
                o -> orderModels.add(new HomeServiceOrderModel().convertOrder2Model(o))
        );
        return orderModels;
    }
}
