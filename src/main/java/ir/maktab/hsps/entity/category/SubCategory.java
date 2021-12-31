package ir.maktab.hsps.entity.category;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class SubCategory extends Category {

    @ManyToOne
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private Set<HomeServiceOrder> homeServiceOrders;

    @ManyToMany(mappedBy = "subCategories", cascade = CascadeType.ALL)
    private Set<Proficient> proficients;
}
