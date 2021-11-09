package ua.com.alevel;

import java.util.List;

public class TestsLogsMain {

    public static void main(String[] args) {
        EntityService entityService = new EntityService();
        for (int i = 0; i < 50_000; i++) {
            EntityCreateDto dto = new EntityCreateDto();
            dto.setEmail("email" + i);
            dto.setPassword("password");
            entityService.create(dto);
        }
        List<EntityDto> list = entityService.findAll();
        for (EntityDto entityDto : list) {
            System.out.println("entityDto = " + entityDto);
        }
    }
}
