package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeServiceOrderRepository extends JpaRepository<HomeServiceOrder, Long> {
}
