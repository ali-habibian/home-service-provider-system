package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
