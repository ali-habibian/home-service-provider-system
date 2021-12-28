package ir.maktab.hsps.entity.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MainCategory extends Category{

    @OneToMany(mappedBy = "mainCategory")
    private Set<SubCategory> subCategorySet;
}