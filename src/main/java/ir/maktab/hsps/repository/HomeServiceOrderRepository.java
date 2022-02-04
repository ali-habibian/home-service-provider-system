package ir.maktab.hsps.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.QHomeServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface HomeServiceOrderRepository extends JpaRepository<HomeServiceOrder, Long>, QuerydslPredicateExecutor<HomeServiceOrder>
        , QuerydslBinderCustomizer<QHomeServiceOrder> {

    @Override
    default void customize(final QuerydslBindings bindings, final QHomeServiceOrder root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    List<HomeServiceOrder> findAllByCustomer_Id(long customerId);
}
