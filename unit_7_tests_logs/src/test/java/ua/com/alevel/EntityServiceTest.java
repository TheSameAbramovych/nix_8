package ua.com.alevel;

import org.junit.jupiter.api.*;

import java.util.List;

import static ua.com.alevel.EntityTestHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityServiceTest {

    private final EntityCreateDto entityCreateDto = new EntityCreateDto();
    private final EntityService entityService = new EntityService();

    @Test
    @Order(1)
    public void shouldDoCreateUserWhenEmailIsNotEmpty() {
        entityCreateDto.setEmail(EMAIL);
        entityCreateDto.setPassword(ORIGINAL_PASSWORD);

        entityService.create(entityCreateDto);

        EntityDto entity = entityService.findByEmail(EMAIL);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.getEmail(), EMAIL);
    }

    @Test
    @Order(2)
    public void shouldDoCreateUserWhenEmailIsEmpty() {
        entityCreateDto.setEmail(null);
        entityCreateDto.setPassword(ORIGINAL_PASSWORD);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> entityService.create(entityCreateDto),
                "email is not present");
    }

    @Test
    @Order(3)
    public void shouldDoReturnUserDtoWhenEmailIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> entityService.findByEmail(null),
                "email is not present");
    }

    @Test
    @Order(4)
    public void shouldDoReturnListEntityDtoWhenResponseIsNotEmpty() {
        List<EntityDto> entities = entityService.findAll();

        Assertions.assertEquals(entities.size(), 1);
    }

    @Test
    @Order(5)
    public void shouldDoUpdateEntityDoesNotThrow() {
        List<EntityDto> entities = entityService.findAll();
        EntityDto dto = entities.get(0);

        Entity entity = new Entity();
        entity.setId(dto.getId());
        entity.setFirstName(FIRST_NAME);
        entity.setLastName(LAST_NAME);

        Assertions.assertDoesNotThrow(() -> entityService.update(entity));
    }

    @Test
    @Order(6)
    public void shouldDoUpdateEntityDoThrow() {
        Entity entity = new Entity();
        entity.setId(null);
        entity.setFirstName(FIRST_NAME);
        entity.setLastName(LAST_NAME);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> entityService.update(entity),
                "user not found by id " + null);
    }

    @Test
    @Order(7)
    public void shouldDoReturnEntityDtoWhenIdIsNotEmpty() {
        List<EntityDto> entities = entityService.findAll();
        EntityDto dto = entities.get(0);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getEmail(), EMAIL);
        Assertions.assertEquals(FULL_NAME, dto.getFullName());
    }

    @Test
    @Order(8)
    public void shouldDoDeleteEntityWhenIdIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> entityService.delete(null),
                "user not found by id " + null);

        Assertions.assertDoesNotThrow(() -> entityService.delete("null"));
    }
}
