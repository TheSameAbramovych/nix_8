package ua.com.alevel.alevel.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.alevel.alevel.entity.Entity;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DBTest {

    @InjectMocks
    private TestDB db;


    @Test
    public void create_ok() {
        Entity entity = new Entity();
        db.create(entity);

        assertNotNull(entity.getId());
        assertEquals(1, db.findAll().length);


    }

    @Test
    public void create_moreThan10Entities_ok() {
        for (int i = 0; i < 12; i++) {
            Entity entity = new Entity();
            db.create(entity);

            assertNotNull(entity.getId());
        }

        assertEquals(12, db.findAll().length);
    }

    @Test
    public void update_ok() {
        Entity entity = new Entity();
        db.create(entity);

        assertNotNull(entity.getId());
        assertEquals(entity, db.findById(entity.getId()));
        assertEquals(1, db.findAll().length);

        Entity entityNew = new Entity();
        entityNew.setId(entity.getId());

        db.update(entityNew);

        assertEquals(entityNew, db.findById(entity.getId()));
        assertNotEquals(entity, db.findById(entity.getId()));
        assertEquals(1, db.findAll().length);
    }

    @Test
    public void delete_ok() {
        Entity entity1 = new Entity();
        db.create(entity1);

        assertNotNull(entity1.getId());
        assertEquals(entity1, db.findById(entity1.getId()));
        assertEquals(1, db.findAll().length);

        db.delete(entity1.getId());

        assertNull(db.findById(entity1.getId()));
        assertEquals(0, db.findAll().length);
    }

    @Test
    public void delete_fromNotSingleElements_ok() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        Entity entity3 = new Entity();
        db.create(entity1);
        db.create(entity2);
        db.create(entity3);

        db.delete(entity2.getId());

        assertNull(db.findById(entity2.getId()));
        assertNotNull(db.findById(entity1.getId()));
        assertNotNull(db.findById(entity3.getId()));
        assertEquals(2, db.findAll().length);
    }


    @Test
    public void findById_and_findAll_ok() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();

        db.create(entity1);
        db.create(entity2);

        assertEquals(entity1, db.findById(entity1.getId()));
        assertEquals(entity2, db.findById(entity2.getId()));

        db.delete(entity1.getId());

        assertNull(db.findById(entity1.getId()));
    }

    @Test
    public void findByAll_ok() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();

        db.create(entity1);
        db.create(entity2);

        assertEquals(2, db.findAll().length);

        db.delete(entity1.getId());

        assertEquals(1, db.findAll().length);
    }

    public static class TestDB extends DB<Entity> {

        @Override
        protected Entity[] createEntitiesArray(int size) {
            return new Entity[size];
        }
    }
}