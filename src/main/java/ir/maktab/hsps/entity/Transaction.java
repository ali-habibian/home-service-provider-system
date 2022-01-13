package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Proficient proficient;

    @OneToOne
    private HomeServiceOrder homeServiceOrder;

    @OneToOne
    private HomeServiceOffer homeServiceOffer;

    private Double amount;
}
