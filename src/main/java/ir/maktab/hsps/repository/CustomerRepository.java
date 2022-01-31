package ir.maktab.hsps.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.hsps.entity.user.*;
import ir.maktab.hsps.entity.user.QCustomer;
import ir.maktab.hsps.entity.user.QProficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer>
        , QuerydslBinderCustomizer<QCustomer> {

    @Override
    default void customize(final QuerydslBindings bindings, final QCustomer root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.password);
    }

    Customer findByEmail(String email);

    List<Customer> findAllByCustomerStatus(UserStatus status);
}
