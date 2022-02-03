package ir.maktab.hsps.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.customer.CustomerUpdateResult;
import ir.maktab.hsps.api.user.proficient.ProficientCreateParam;
import ir.maktab.hsps.api.user.proficient.ProficientModel;
import ir.maktab.hsps.api.user.proficient.ProficientUpdateParam;
import ir.maktab.hsps.api.user.proficient.ProficientUpdateResult;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.querydsl.proficient.ProficientPredicatesBuilder;
import ir.maktab.hsps.service.ProficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //    http://localhost:8080/proficients/confirm?token=3ceaa13f-c507-4c78-9c9c-e37822689caa
    @GetMapping(path = {"confirm"})
    public String confirmToken(@RequestParam("token") String token) {
        return proficientService.confirmToken(token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{proficientId}/confirm")
    public ResponseEntity<ProficientUpdateResult> confirmProficientByAdmin(@PathVariable long proficientId) {
        ProficientUpdateResult proficientUpdateResult = proficientService.confirmProficientByAdmin(proficientId);
        return ResponseEntity.ok(proficientUpdateResult);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public Iterable<Proficient> findAllByQuerydsl(@RequestParam(value = "search") String search) {
        System.out.println("search = " + search);
        ProficientPredicatesBuilder builder = new ProficientPredicatesBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return proficientService.findAll(exp);
    }
}
