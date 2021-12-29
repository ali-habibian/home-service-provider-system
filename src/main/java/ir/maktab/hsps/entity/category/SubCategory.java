package ir.maktab.hsps.entity.category;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
public class SubCategory extends Category{

    @ManyToOne
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private Set<HomeServiceOrder> homeServiceOrders;
}
