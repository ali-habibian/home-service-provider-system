package ir.maktab.hsps.entity;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class HomeServiceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Proficient proficient;

    @CreationTimestamp
    private Instant offerCreateDate;

    private Double suggestedPrice;

    private String workDuration;

    private Instant startTime;

    @ManyToOne
    private HomeServiceOrder homeServiceOrder;

    private Boolean isAccepted;
}
