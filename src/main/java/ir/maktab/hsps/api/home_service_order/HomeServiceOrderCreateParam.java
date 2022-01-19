package ir.maktab.hsps.api.home_service_order;

import ir.maktab.hsps.api.address.AddressCreateParam;
import ir.maktab.hsps.api.address.AddressUpdateParam;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOrderCreateParam {
    private long customerId;
    private long subCategoryId;
    private Double suggestedPrice;
    private String comment;
    private AddressCreateParam address;

    public HomeServiceOrder convert2HomeServiceOrder(Customer customer, SubCategory subCategory) {
        return HomeServiceOrder.builder()
                .customer(customer)
                .subCategory(subCategory)
                .suggestedPrice(this.suggestedPrice)
                .comment(this.comment)
                .address(address.convert2Address())
                .build();
    }
}
