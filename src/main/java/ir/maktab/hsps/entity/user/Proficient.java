package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Review;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.category.SubCategory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
public class Proficient extends User {

    private UserStatus proficientStatus;

    @CreationTimestamp
    private Instant registerDate;

    private Double credit = 0.0;

//    @Lob
//    private Blob profileImg;
    private String profileImgUrl; // Max size: 300 KB

    @OneToMany(mappedBy = "proficient", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @ManyToMany
    private Set<SubCategory> subCategories;
}
