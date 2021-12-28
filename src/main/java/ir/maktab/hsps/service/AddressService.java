package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AddressService extends BaseService<Address, Long> {
    private final AddressRepository addressRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(addressRepository);
    }
}
