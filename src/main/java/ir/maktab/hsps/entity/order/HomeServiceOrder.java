package ir.maktab.hsps.entity.order;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.user.Customer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
public class HomeServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private SubCategory subCategory;

    private Double suggestedPrice;
    private String comment;

    @CreationTimestamp
    private Instant orderCreateDate;

    private Instant orderFinishedDate;

    @ManyToOne
    private Address address;

    private OrderStatus orderStatus = OrderStatus.WAITING_FOR_PROFICIENT_SUGGESTION;

    @OneToMany(mappedBy = "homeServiceOrder", cascade = CascadeType.ALL)
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToOne(cascade = CascadeType.ALL)
    private HomeServiceOffer acceptedOffer;
}
