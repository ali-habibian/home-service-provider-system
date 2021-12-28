package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Admin;
import ir.maktab.hsps.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService<Admin, Long> {
    private final AdminRepository adminRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(adminRepository);
    }
}
