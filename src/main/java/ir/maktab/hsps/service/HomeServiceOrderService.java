package ir.maktab.hsps.service;

import ir.maktab.hsps.api.home_service_order.HomeServiceOrderCreateParam;
import ir.maktab.hsps.api.home_service_order.HomeServiceOrderCreateResult;
import ir.maktab.hsps.api.user.customer.CustomerModel;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.repository.HomeServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public HomeServiceOrder update(HomeServiceOrder homeServiceOrder) {
        return null;
    }

    @Transactional
    public HomeServiceOrder acceptOffer(HomeServiceOrder homeServiceOrder) {
        HomeServiceOffer acceptedOffer = homeServiceOrder.getAcceptedOffer();

        acceptedOffer.setIsAccepted(true);

        homeServiceOrder.setAcceptedOffer(acceptedOffer);
        homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_TO_COME);

        return homeServiceOrderRepository.save(homeServiceOrder);
    }
}
