package ir.maktab.hsps.api.category.main_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainCategoryUpdateParam {
    private Long id;
    private String name;
}


