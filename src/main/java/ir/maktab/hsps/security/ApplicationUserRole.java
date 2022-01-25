package ir.maktab.hsps.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ir.maktab.hsps.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, PROFICIENT_READ, PROFICIENT_WRITE, ORDER_READ, OFFER_READ)),
    CUSTOMER(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, ORDER_READ, ORDER_WRITE, OFFER_READ)),
    PROFICIENT(Sets.newHashSet(PROFICIENT_READ, PROFICIENT_WRITE, ORDER_READ, OFFER_READ, OFFER_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
