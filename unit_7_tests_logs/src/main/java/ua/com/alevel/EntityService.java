package ua.com.alevel;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EntityService {

    public void create(EntityCreateDto dto) {
        if (StringUtils.isNotBlank(dto.getEmail())) {
            if (!EntityDao.getInstance().existByEmail(dto.getEmail())) {
                Entity entity = new Entity();
                entity.setEmail(dto.getEmail());
                entity.setPassword(EntityUtil.generateEncryptPassword(dto.getPassword()));
                EntityDao.getInstance().create(entity);
            }
        } else {
            throw new RuntimeException("email is not present");
        }
    }

    public EntityDto findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new RuntimeException("email is not present");
        }
        return new EntityDto(EntityDao.getInstance().findByEmail(email));
    }

    public List<EntityDto> findAll() {
        return EntityDao.getInstance()
                .findAll()
                .stream()
                .map(EntityDto::new)
                .collect(Collectors.toList());
    }

    public void update(Entity entity) {
        EntityDao.getInstance().update(entity);
    }

    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            EntityDao.getInstance().delete(id);
        } else {
            throw new RuntimeException("user not found by id " + id);
        }
    }
}
