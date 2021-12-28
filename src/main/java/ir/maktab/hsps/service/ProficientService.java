package ir.maktab.hsps.service;

import ir.maktab.hsps.repository.ProficientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProficientService {
    private final ProficientRepository proficientRepository;


}
