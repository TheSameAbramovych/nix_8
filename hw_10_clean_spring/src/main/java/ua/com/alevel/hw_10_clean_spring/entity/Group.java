package ua.com.alevel.hw_10_clean_spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "students_groups")
public class Group extends BaseEntity {

    @ManyToMany(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "students_to_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    @NotEmpty(message = "Can't be empty")
    private String name;

    @ManyToOne
    @JoinColumn(name = "headman_id")
    @NotNull(message = "Headman can`t be null")
    private Student headman;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (name != group.name) return false;
        return students != null ? students.equals(group.students) : group.students == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        return result;
    }


}
