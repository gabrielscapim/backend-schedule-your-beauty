package schedule.your.beauty.api.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import schedule.your.beauty.api.dto.DataAddScheduleDTO;
import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.ClientRepository;
import schedule.your.beauty.api.repository.ProductionRepository;
import schedule.your.beauty.api.repository.ScheduleRepository;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = {
        "/sql/create_clients_db.sql",
        "/sql/create_productions_db.sql",
        "/sql/create_schedules_db.sql",
        "/sql/create_scheduling_date_times_db.sql",
        "/sql/create_scheduled_date_times_db.sql",
})
public class ScheduleIntegrationTests {

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
    ScheduleRepository scheduleRepository;

    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SchedulingDateTimeRepository schedulingDateTimeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should get schedules by day")
    void getSchedulesByDaySucess() throws Exception {
        this.createScheduleInDB();

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/schedule/2023-01-01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"clientName\":\"Gabriel\",\"date\":\"01/01/2023\",\"startTime\":\"11:00:00\",\"productionName\":\"Penteado\",\"eventName\":\"Formatura\",\"clientNumber\":\"99999999999\"}]"));
    }

    @Test
    @DisplayName("Should dete delete schedule")
    void deleteScheduleSucess() throws Exception {
        Schedule newSchedule = this.createScheduleInDB();
        Schedule scheduleFromDB = scheduleRepository.getReferenceById(1);

        assertThat(newSchedule.getId()).isEqualTo(scheduleFromDB.getId());

        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/schedule/1"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    @DisplayName("Should add schedule")
    void addScheduleSucess() throws Exception {
        SchedulingDateTime schedulingDateTime = new SchedulingDateTime(
                "2023-01-01 11:00:00",
                false,
                true
        );
        Production production = new Production("Penteado", 50);
        schedulingDateTimeRepository.save(schedulingDateTime);
        productionRepository.save(production);

        String content = "{\n" +
                "  \"clientName\": \"Gabriel\",\n" +
                "  \"clientNumber\": \"99999999999\",\n" +
                "  \"productionName\": \"Penteado\",\n" +
                "  \"eventName\": \"Formatura\",\n" +
                "  \"schedulingDateTime\": \"2023-01-01 11:00:00\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private Schedule createScheduleInDB() {
        Client client = new Client("Gabriel", "99999999999");
        Production production = new Production("Penteado", 50);
        List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();
        schedulingDateTimes.add(new SchedulingDateTime(
                "2023-01-01 11:00:00",
                false,
                true
        ));

        productionRepository.save(production);
        clientRepository.save(client);
        schedulingDateTimeRepository.saveAll(schedulingDateTimes);

        Schedule schedule = new Schedule(
                client,
                production,
                schedulingDateTimes,
                "Formatura"
        );


        scheduleRepository.save(schedule);

        return schedule;
    }
}
