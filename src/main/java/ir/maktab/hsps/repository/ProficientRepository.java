package ir.maktab.hsps.repository;

import com.querydsl.core.types.dsl.EnumExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.QProficient;
import ir.maktab.hsps.entity.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface ProficientRepository extends JpaRepository<Proficient, Long>, QuerydslPredicateExecutor<Proficient>
        , QuerydslBinderCustomizer<QProficient> {

    @Override
    default void customize(final QuerydslBindings bindings, final QProficient root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.password);
    }

    Proficient findByEmail(String email);

    List<Proficient> findAllByProficientStatus(UserStatus status);

    List<Proficient> findAllBySubCategoriesId(long subCategoryId);
}
