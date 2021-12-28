package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Proficient proficient;

    @OneToOne(cascade = CascadeType.ALL)
    private HomeServiceOrder homeServiceOrder;

    @OneToOne(cascade = CascadeType.ALL)
    private HomeServiceOffer homeServiceOffer;
}
