package ir.maktab.hsps.api.user.customer;

import ir.maktab.hsps.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateParam {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String currentPassword;
//    private AddressUpdateParam address;

    public Customer convert2Customer() {
        return Customer.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.currentPassword)
//                .address(address.convert2Address())
                .build();
    }
}
