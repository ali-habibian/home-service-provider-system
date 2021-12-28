package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private Integer rating;
    private Instant reviewTime;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Proficient proficient;

    @OneToOne(cascade = CascadeType.ALL)
    private HomeServiceOffer homeServiceOffer;
}
