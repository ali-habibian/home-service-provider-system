package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User {
    @Column(name = "customer_status")
    private UserStatus customerStatus;

    @Column(name = "register_date")
    @CreationTimestamp
    private Instant registerDate;

    private Double credit;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "customer")
    private Set<HomeServiceOrder> homeServiceOrders;

    @OneToMany(mappedBy = "customer")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "customer")
    private Set<Transaction> transactions;
}
