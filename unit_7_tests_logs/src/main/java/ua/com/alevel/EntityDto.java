package ua.com.alevel;

public class EntityDto {

    private String id;
    private String email;
    private String fullName;
    private Long created;

    public EntityDto() {
    }

    public EntityDto(Entity entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.fullName = entity.getFirstName() + " " + entity.getLastName();
        this.created = entity.getCreated().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "EntityDto{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", created=" + created +
                '}';
    }
}
