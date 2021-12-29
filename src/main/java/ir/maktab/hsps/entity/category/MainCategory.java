package ir.maktab.hsps.entity.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
public class MainCategory extends Category {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mainCategory")
    private Set<SubCategory> subCategorySet;
}
