package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.proficient.ProficientCreateParam;
import ir.maktab.hsps.api.user.proficient.ProficientModel;
import ir.maktab.hsps.api.user.proficient.ProficientUpdateParam;
import ir.maktab.hsps.api.user.proficient.ProficientUpdateResult;
import ir.maktab.hsps.service.ProficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //    @PreAuthorize("hasAnyRole('ROLE_PROFICIENT')")
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<UserChangePasswordResult> changePassword(@RequestBody UserChangePasswordParam changePasswordParam,
                                                                   @PathVariable Long id) {
        changePasswordParam.setUserId(id);
        UserChangePasswordResult userChangePasswordResult = proficientService.changePassword(changePasswordParam);
        return ResponseEntity.ok(userChangePasswordResult);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<ProficientUpdateResult> update(@PathVariable long id, @RequestBody ProficientUpdateParam updateParam) {
        updateParam.setId(id);
        ProficientUpdateResult proficientUpdateResult = null;
        try {
            proficientUpdateResult = proficientService.updateProficient(updateParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(proficientUpdateResult);
    }
}
