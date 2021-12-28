package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Proficient;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class HomeServiceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Proficient proficient;

    @CreationTimestamp
    private Instant offerCreateDate;

    private Double suggestedPrice;

    private String workDuration;

    private String startTime;

    @ManyToOne(cascade = CascadeType.ALL)
    private HomeServiceOrder homeServiceOrder;

    private Boolean isAccepted;
}
