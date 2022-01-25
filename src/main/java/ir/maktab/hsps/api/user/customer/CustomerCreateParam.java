package ir.maktab.hsps.api.user.customer;

import ir.maktab.hsps.api.address.AddressCreateParam;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserRole;
import ir.maktab.hsps.security.ApplicationUserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateParam {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private ApplicationUserRole userRole;
//    private AddressCreateParam address;

    public Customer convert2Customer() {
        return Customer.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
//                .applicationUserRole(this.userRole)
//                .address(address.convert2Address())
                .build();
    }
}
