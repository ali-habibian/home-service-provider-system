package ir.maktab.hsps.api.user.customer;

import ir.maktab.hsps.api.address.AddressModel;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserRole;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double credit;
    private UserStatus customerStatus;
    private ApplicationUserRole userRole;
    private Instant registerDate;
//    private AddressModel addressModel;

    public CustomerModel convertCustomer2Model(Customer customer) {
        return CustomerModel.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .credit(customer.getCredit())
                .customerStatus(customer.getCustomerStatus())
                .userRole(customer.getApplicationUserRole())
                .registerDate(customer.getRegisterDate())
//                .addressModel(new AddressModel().convertAddress2Model(customer.getAddress()))
                .build();
    }
}
