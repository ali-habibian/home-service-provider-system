package ir.maktab.hsps.entity.category;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubCategory extends Category{

    @ManyToOne(cascade = CascadeType.ALL)
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private Set<HomeServiceOrder> homeServiceOrders;
}
