package ua.com.alevel.entity;

import ua.com.alevel.utils.CustomList;

import java.util.Objects;

public class Group extends Entity {
    private String id;
    private String name;
    private String headman;
    private CustomList<String> studentIds = new CustomList<>();

    public String getHeadman() {
        return headman;
    }

    public void setHeadman(String headman) {
        this.headman = headman;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeStudentId(String id) {
        studentIds.remove(studentIds.indexOf(id));
    }

    public void removeAllStudents() {
        studentIds = new CustomList<>();
    }

    public CustomList<String> getStudentIds() {
        return studentIds;
    }

    public void addStudentId(String id) {
        studentIds.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return Objects.equals(id, group.id)
                && Objects.equals(name, group.name)
                && Objects.equals(headman, group.headman);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, headman);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", headman='" + headman + '\'' +
                ", studentIds=" + studentIds +
                '}';
    }
}
