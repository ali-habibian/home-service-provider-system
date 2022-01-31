package ir.maktab.hsps.querydsl.customer;

import com.querydsl.core.types.dsl.*;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.User;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.querydsl.SearchCriteria;
import ir.maktab.hsps.util.Utility;

import java.time.Instant;

public class CustomerPredicate {
    private SearchCriteria criteria;
    private final Utility utility;

    public CustomerPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
        this.utility = new Utility();
    }

    public BooleanExpression getPredicate() {
        final PathBuilder<Customer> entityPath = new PathBuilder<>(Customer.class, "customer");

        if (isNumeric(criteria.getValue().toString())) {
            final NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            final int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
            }
        } else if (isEnum(criteria.getValue().toString())) {
            final EnumPath<UserStatus> path = entityPath.getEnum(criteria.getKey(), UserStatus.class);
            final UserStatus value = Enum.valueOf(UserStatus.class, criteria.getValue().toString());
            if (":".equals(criteria.getOperation())) {
                return path.eq(value);
            }
        } else if (isDate(criteria.getValue().toString())) {
            final DateTimePath<Instant> path = entityPath.getDateTime(criteria.getKey(), Instant.class);
            final Instant value = utility.convertStringDateToInstant(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ">":
                    return path.after(value);
                case "<":
                    return path.before(value);
            }
        } else if (isBoolean(criteria.getValue().toString())) {
            final BooleanPath path = entityPath.getBoolean(criteria.getKey());
            final Boolean value = Boolean.valueOf(criteria.getValue().toString());
            if (":".equals(criteria.getOperation())) {
                return path.eq(value);
            }
        } else {
            final StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isEnum(final String str) {
        try {
            Enum.valueOf(UserStatus.class, str);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    private boolean isBoolean(final String str) {
        return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
    }

    private boolean isDate(final String str) {
        try {
            Instant instant = utility.convertStringDateToInstant(str);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }
}
