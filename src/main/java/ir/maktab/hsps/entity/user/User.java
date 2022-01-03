package ir.maktab.hsps.entity.user;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email; // Must be unique

    @Column(nullable = false)
    private String password; // Minimum size:8, combination of letters and numbers
}
