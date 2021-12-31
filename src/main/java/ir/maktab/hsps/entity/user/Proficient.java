package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Review;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.category.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    private Double credit = 0.0;

    private String profileImgUrl; // Max size: 300 KB

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @ManyToMany
    private Set<SubCategory> subCategories;
}
