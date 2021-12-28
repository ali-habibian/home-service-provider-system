package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Proficient extends User {

    private UserStatus proficientStatus;

    @CreationTimestamp
    private Instant registerDate;

    private Double credit;

    private String profileImgUrl; // Max size: 300 KB

    @OneToMany(mappedBy = "proficient")
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToMany(mappedBy = "proficient")
    private Set<Review> reviews;
}
