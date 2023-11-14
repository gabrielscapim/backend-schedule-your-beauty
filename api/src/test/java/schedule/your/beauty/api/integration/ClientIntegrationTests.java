package schedule.your.beauty.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.User;
import schedule.your.beauty.api.repository.ClientRepository;
import schedule.your.beauty.api.repository.UserRepository;

import java.util.Map;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = "/sql/create_clients_db.sql")
public class ClientIntegrationTests {

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
    ClientRepository clientRepository;

    @Test
    void getAllClientsSucess() throws Exception {
        Client clientOne = new Client("Gabriel", "554499999999");
        Client clientTwo = new Client("Maria", "554488888888");

        Client savedClientOne = clientRepository.save(clientOne);
        Client savedClientTwo = clientRepository.save(clientTwo);

        mockMvc.perform(MockMvcRequestBuilders.
                get("/client"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"name\":\"Gabriel\",\"number\":\"554499999999\"},{\"id\":2,\"name\":\"Maria\",\"number\":\"554488888888\"}]"));
    }
}
