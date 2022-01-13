package ir.maktab.hsps.entity.user;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Review;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.category.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Proficient extends User {

    private UserStatus proficientStatus = UserStatus.NEW;

    @CreationTimestamp
    private Instant registerDate;

    private Double credit = 0.0;

    //    @Lob
//    private Blob profileImg;
//    private String profileImgUrl; // Max size: 300 KB

//    @Lob
//    private byte[] profileImage;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String profileImage;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<SubCategory> subCategories;

    public void addHomeServiceOffer(HomeServiceOffer homeServiceOffer) {
        if (homeServiceOffers == null) {
            homeServiceOffers = new HashSet<>();
        }
        homeServiceOffers.add(homeServiceOffer);
        homeServiceOffer.setProficient(this);
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new HashSet<>();
        }
        reviews.add(review);
        review.setProficient(this);
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new HashSet<>();
        }
        transactions.add(transaction);
        transaction.setProficient(this);
    }
}
