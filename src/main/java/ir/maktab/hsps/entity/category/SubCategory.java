package ir.maktab.hsps.entity.category;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class SubCategory extends Category {

    @ManyToOne
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    private Set<HomeServiceOrder> homeServiceOrders;

    @ManyToMany(mappedBy = "subCategories")
    private Set<Proficient> proficients;


}
