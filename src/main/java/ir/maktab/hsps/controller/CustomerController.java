package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.customer.CustomerCreateParam;
import ir.maktab.hsps.api.user.customer.CustomerCreateResult;
import ir.maktab.hsps.api.user.customer.CustomerUpdateParam;
import ir.maktab.hsps.api.user.customer.CustomerUpdateResult;
import ir.maktab.hsps.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerCreateResult> save(@RequestBody CustomerCreateParam createParam) {
        CustomerCreateResult customerCreateResult = customerService.saveCustomer(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreateResult);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<UserChangePasswordResult> changePassword(@RequestBody UserChangePasswordParam changePasswordParam,
                                                                   @PathVariable Long id) {
        changePasswordParam.setUserId(id);
        UserChangePasswordResult userChangePasswordResult = customerService.changePassword(changePasswordParam);
        return ResponseEntity.ok(userChangePasswordResult);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerUpdateResult> update(@PathVariable long id, @RequestBody CustomerUpdateParam updateParam) {

//        CustomerModel customerModel = customerService.loadByIdReturnModel(id);
//        Long addressId = customerModel.getAddressModel().getId();
//        AddressUpdateParam address = updateParam.getAddress();
//        address.setId(addressId);
//        updateParam.setAddress(address);

        updateParam.setId(id);

        CustomerUpdateResult customerUpdateResult = customerService.update(updateParam);
        return ResponseEntity.ok(customerUpdateResult);
    }

    //    http://localhost:8080/customers/confirm?token=3ceaa13f-c507-4c78-9c9c-e37822689caa
    @GetMapping(path = {"confirm"})
    public String confirm(@RequestParam("token") String token) {
        return customerService.confirmToken(token);
    }
}
