package ua.com.alevel.utils;

import java.util.Objects;

public class CustomList<T> {

    private int pointer;
    private T[] entities;

    public CustomList() {
        entities = createEntitiesArray(10);
        pointer = 0;
    }

    public int indexOf(T entity) {
        for (int i = 0; i < pointer; i++) {
            if (Objects.equals(entity, entities[i])) {
                return i;
            }
        }
        return -1;
    }

    public void add(T entity) {
        if (pointer == entities.length) {
            copyToNewArray();
        }
        entities[pointer] = entity;
        pointer++;
    }

    public void remove(int index) {
        if (index >= 0 && index < pointer) {
            System.arraycopy(entities, index + 1, entities, index, pointer - index - 1);
            pointer--;
        }
    }


    public T get(int index) {
        if (index >= 0 && index < pointer) {
            return entities[index];
        }
        return null;
    }

    public int getLength() {
        return pointer;
    }

    private void copyToNewArray() {
        T[] usersTmp = entities;
        entities = createEntitiesArray((entities.length * 2 / 3) + 1 + entities.length);
        System.arraycopy(usersTmp, 0, entities, 0, pointer);
    }

    protected T[] createEntitiesArray(int size) {
        return (T[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < pointer; i++) {
            stringBuilder.append(entities[i]);
            if (i != pointer - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
