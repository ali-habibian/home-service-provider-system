package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.user.customer.CustomerListResult;
import ir.maktab.hsps.api.user.proficient.ProficientListResult;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.service.CustomerService;
import ir.maktab.hsps.service.ProficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final CustomerService customerService;
    private final ProficientService proficientService;


    @GetMapping("/{id}/customers")
    public ResponseEntity<CustomerListResult> showAllCustomers(@PathVariable Long id) {
        CustomerListResult customerListResult = customerService.loadAllCustomers();
        return ResponseEntity.ok(customerListResult);
    }

    @GetMapping("/{id}/customers/{status}")
    public ResponseEntity<CustomerListResult> showAllCustomersByStatus(@PathVariable UserStatus status, @PathVariable Long id) {
        CustomerListResult customerListResult = customerService.loadAllCustomersByStatus(status);
        return ResponseEntity.ok(customerListResult);
    }

    @GetMapping("/{id}/proficients")
    public ResponseEntity<ProficientListResult> showAllProficients(@PathVariable Long id) {
        ProficientListResult proficientListResult = proficientService.loadAllProficients();
        return ResponseEntity.ok(proficientListResult);
    }

    @GetMapping("/{id}/proficients/{status}")
    public ResponseEntity<ProficientListResult> showAllProficientsByStatus(@PathVariable UserStatus status, @PathVariable Long id) {
        ProficientListResult proficientListResult = proficientService.loadAllProficientsByStatus(status);
        return ResponseEntity.ok(proficientListResult);
    }

    @GetMapping("/{id}/proficients/subCategory/{subCategoryId}")
    public ResponseEntity<ProficientListResult> showAllProficientsBySubCategoryId(@PathVariable long subCategoryId, @PathVariable Long id) {
        ProficientListResult proficientListResult = proficientService.loadAllProficientsBySubCategoryId(subCategoryId);
        return ResponseEntity.ok(proficientListResult);
    }
}
