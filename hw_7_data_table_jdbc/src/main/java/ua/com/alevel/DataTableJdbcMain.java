package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.jpa.JpaConfig;

@SpringBootApplication
public class DataTableJdbcMain {

    private final JpaConfig jpaConfig;

    public DataTableJdbcMain(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataTableJdbcMain.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        jpaConfig.connect();
    }
}
