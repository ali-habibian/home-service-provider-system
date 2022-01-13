package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.proficient.ProficientCreateParam;
import ir.maktab.hsps.api.user.proficient.ProficientModel;
import ir.maktab.hsps.service.ProficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proficients")
public class ProficientController {

    private final ProficientService proficientService;

    @PostMapping
    public ResponseEntity<ProficientModel> save(@ModelAttribute ProficientCreateParam createParam) throws IOException {
        ProficientModel response = proficientService.save(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserChangePasswordResult> changePassword(@RequestBody UserChangePasswordParam changePasswordParam,
                                                                   @PathVariable Long id) {
        changePasswordParam.setUserId(id);
        UserChangePasswordResult userChangePasswordResult = proficientService.changePassword(changePasswordParam);
        return ResponseEntity.ok(userChangePasswordResult);
    }
}