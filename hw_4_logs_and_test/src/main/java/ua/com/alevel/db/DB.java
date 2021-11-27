package ua.com.alevel.db;

import ua.com.alevel.entity.Entity;

import java.util.Objects;
import java.util.UUID;

public abstract class DB<T extends Entity> {

    private int pointer;
    private T[] entities;

    public DB() {
        entities = createEntitiesArray(10);
        pointer = 0;
    }

    public void create(T entity) {
        if (pointer == entities.length) {
            copyToNewArray();
        }
        entity.setId(generateId());
        entities[pointer] = entity;
        pointer++;
    }

    public void update(T entity) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] == null) {
                break;
            }
            if (Objects.equals(entities[i].getId(), entity.getId())) {
                entities[i] = entity;
                return;
            }
        }
    }

    public void delete(String id) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] == null) {
                break;
            }
            if (Objects.equals(entities[i].getId(), id)) {
                T[] entitiesTmp = createEntitiesArray(pointer);
                System.arraycopy(entities, i + 1, entitiesTmp, i, pointer - i - 1);
                System.arraycopy(entities, 0, entitiesTmp, 0, i);
                entities = entitiesTmp;
                pointer--;
                return;
            }
        }
    }

    public T findById(String id) {
        for (T entity : entities) {
            if (entity == null) {
                break;
            }
            if (Objects.equals(entity.getId(), id)) {
                return entity;
            }
        }
        return null;
    }

    public T[] findAll() {
        T[] entitiesTmp = createEntitiesArray(pointer);
        System.arraycopy(entities, 0, entitiesTmp, 0, pointer);
        return entitiesTmp;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (findById(id) != null) {
            return generateId();
        }
        return id;
    }

    private void copyToNewArray() {
        T[] entitiesTmp = this.entities;
        this.entities = createEntitiesArray((this.entities.length * 2 / 3) + 1 + this.entities.length);
        System.arraycopy(entitiesTmp, 0, this.entities, 0, pointer);
    }

    protected abstract T[] createEntitiesArray(int size);
}
