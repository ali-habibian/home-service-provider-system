package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.repository.ProficientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ProficientService extends BaseService<Proficient, Long> {
    private final ProficientRepository proficientRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(proficientRepository);
    }
}
