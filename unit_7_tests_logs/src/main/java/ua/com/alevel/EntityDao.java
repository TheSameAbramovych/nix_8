package ua.com.alevel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EntityDao {

    private static EntityDao instance;

    private final List<Entity> entities = new ArrayList<>();

    private EntityDao() {
    }

    public static EntityDao getInstance() {
        if (instance == null) {
            instance = new EntityDao();
        }
        return instance;
    }

    public void create(Entity entity) {
        entity.setId(genId());
        entity.setCreated(new Date());
        entities.add(entity);
    }

    public void update(Entity entity) {
        Entity current = findById(entity.getId());
        current.setFirstName(entity.getFirstName());
        current.setLastName(entity.getLastName());
    }

    public Entity findById(String id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user not found by id " + id));
    }

    public Entity findByEmail(String email) {
        return entities.stream()
                .filter(entity -> entity.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user not found by email " + email));
    }

    public boolean existByEmail(String email) {
        return entities
                .stream()
                .anyMatch(entity -> entity.getEmail().equals(email));
    }

    public void delete(String id) {
        entities.removeIf(entity -> entity.getId().equals(id));
    }

    public List<Entity> findAll() {
        return entities;
    }

    private String genId() {
        String id = UUID.randomUUID().toString();
        if (entities.stream().anyMatch(entity -> entity.getId().equals(id))) {
            return genId();
        }
        return id;
    }
}
