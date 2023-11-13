package schedule.your.beauty.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.repository.ProductionRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = "/sql/create_productions_db.sql")
public class ProductionIntegrationTests {

    @Container
    public static MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:8.0.29")
            .withDatabaseName("schedule_your_beauty_database");

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductionRepository productionRepository;

    @Test
    void getAllProductionsSucess() throws Exception {
        Production productionOne = new Production("Penteado", 50);
        Production productionTwo = new Production("Maquiagem", 130);

        productionRepository.save(productionOne);
        productionRepository.save(productionTwo);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/production"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"name\":\"Penteado\",\"price\":50},{\"id\":2,\"name\":\"Maquiagem\",\"price\":130}]"));
    }
}
