package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private Integer rating;
    private Instant reviewTime;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Proficient proficient;

    @OneToOne
    private HomeServiceOffer homeServiceOffer;
}