package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User {
    private UserStatus customerStatus;

    @CreationTimestamp
    private Instant registerDate;

    private Double credit = 0.0;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<HomeServiceOrder> homeServiceOrders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
}
