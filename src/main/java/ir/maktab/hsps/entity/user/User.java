package ir.maktab.hsps.entity.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email; // Must be unique

    @Column(nullable = false)
    private String password; // Minimum size:8, combination of letters and numbers

    @Column(nullable = false)
    private UserRole userRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userRole.hashCode();
        return result;
    }
}
