package ir.maktab.hsps.entity.category;

import ir.maktab.hsps.entity.order.HomeServiceOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubCategory extends Category{

    @ManyToOne
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    private Set<HomeServiceOrder> homeServiceOrders;
}
