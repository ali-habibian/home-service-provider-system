package ir.maktab.hsps.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.QHomeServiceOffer;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.QCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface HomeServiceOfferRepository extends JpaRepository<HomeServiceOffer, Long>,
        QuerydslPredicateExecutor<HomeServiceOffer>, QuerydslBinderCustomizer<QHomeServiceOffer> {

    @Override
    default void customize(final QuerydslBindings bindings, final QHomeServiceOffer root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    List<HomeServiceOffer> findAllByHomeServiceOrder_IdOrderBySuggestedPriceAsc(long orderId);

    List<HomeServiceOffer> findAllByProficient_Id(long proficientId);

    @Override
    List<HomeServiceOffer> findAll(Sort sort);
}