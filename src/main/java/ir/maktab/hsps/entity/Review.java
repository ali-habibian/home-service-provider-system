package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private Integer rating;

    @CreationTimestamp
    private Instant reviewTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Proficient proficient;

    @OneToOne
    private HomeServiceOffer homeServiceOffer;
}
