package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Customer extends User {
    private UserStatus customerStatus = UserStatus.NEW;

    @CreationTimestamp
    private Instant registerDate;

    @Column(nullable = false)
    private Double credit = 0.0;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<HomeServiceOrder> homeServiceOrders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public void addHomeServiceOrder(HomeServiceOrder homeServiceOrder) {
        if (homeServiceOrders == null) {
            homeServiceOrders = new HashSet<>();
        }
        homeServiceOrders.add(homeServiceOrder);
        homeServiceOrder.setCustomer(this);
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new HashSet<>();
        }
        reviews.add(review);
        review.setCustomer(this);
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new HashSet<>();
        }
        transactions.add(transaction);
        transaction.setCustomer(this);
    }
}
