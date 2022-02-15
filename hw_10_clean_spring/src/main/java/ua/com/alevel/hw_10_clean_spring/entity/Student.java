package ua.com.alevel.hw_10_clean_spring.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends BaseEntity {
    @ManyToMany(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "students"
    )
    private Set<Group> groups = new HashSet<>();

    @NotEmpty(message = "First name is empty")
    private String firstName;

    @NotEmpty(message = "Last name is empty")
    private String lastName;

    @Min(value = 1, message = "Age can`t be less than 1")
    @Max(value = 100, message = "Age can`t be more than 100")
    private int age;

    @NotEmpty(message = "Email is empty")
    @Email(message = "Email is incorrect")
    private String email;

    @Override
    public String toString() {
        return
                "ID: " + getId() + ' ' + firstName + ' ' + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return getId() != null && Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
