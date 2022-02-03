package ir.maktab.hsps.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.customer.CustomerCreateParam;
import ir.maktab.hsps.api.user.customer.CustomerCreateResult;
import ir.maktab.hsps.api.user.customer.CustomerUpdateParam;
import ir.maktab.hsps.api.user.customer.CustomerUpdateResult;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.querydsl.customer.CustomerPredicatesBuilder;
import ir.maktab.hsps.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        updateParam.setId(id);

        CustomerUpdateResult customerUpdateResult = customerService.update(updateParam);
        return ResponseEntity.ok(customerUpdateResult);
    }

    //    http://localhost:8080/customers/confirm?token=3ceaa13f-c507-4c78-9c9c-e37822689caa
    @GetMapping(path = {"confirm"})
    public String confirmToken(@RequestParam("token") String token) {
        return customerService.confirmToken(token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{customerId}/confirm")
    public ResponseEntity<CustomerUpdateResult> confirmCustomerByAdmin(@PathVariable long customerId) {
        CustomerUpdateResult customerUpdateResult = customerService.confirmCustomerByAdmin(customerId);
        return ResponseEntity.ok(customerUpdateResult);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<Customer> findAllByQuerydsl(@RequestParam(value = "search") String search) {
        System.out.println("search = " + search);
        CustomerPredicatesBuilder builder = new CustomerPredicatesBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return customerService.findAll(exp);
    }

}
