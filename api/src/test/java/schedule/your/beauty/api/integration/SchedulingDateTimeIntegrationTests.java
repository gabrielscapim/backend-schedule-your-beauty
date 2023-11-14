package schedule.your.beauty.api.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

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
public class SchedulingDateTimeIntegrationTests {

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
    SchedulingDateTimeRepository schedulingDateTimeRepository;

    private List<SchedulingDateTime> addSchedulingDateTimes() {
        List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();

        String[] availableDateTimes = {
                "2023-01-01 15:00:00",
                "2023-01-01 15:30:00",
                "2023-01-01 16:30:00",
                "2023-01-01 18:00:00",
                "2023-01-01 18:30:00",
                "2023-01-01 19:00:00",
                "2023-01-01 19:30:00",
                "2023-01-01 20:30:00"};

        String[] notAvailableDateTimes = {
                "2023-01-01 16:00:00",
                "2023-01-01 17:00:00"};

        String lastDateTimeToSchedule = "2023-01-01 21:00:00";

        for(String dateTime : availableDateTimes) {
            schedulingDateTimes.add(new SchedulingDateTime(dateTime, false, true));
        }

        for(String dateTime : notAvailableDateTimes) {
            schedulingDateTimes.add(new SchedulingDateTime(dateTime, false, false));
        }

        schedulingDateTimes.add(new SchedulingDateTime(lastDateTimeToSchedule, true, true));
        schedulingDateTimeRepository.saveAll(schedulingDateTimes);

        return schedulingDateTimes;
    }

    @Test
    @DisplayName("Should return available scheduling times for hair")
    void getAvailableSchedulingTimesByDayForHairSucess() throws Exception {
        schedulingDateTimeRepository.deleteAll();
        this.addSchedulingDateTimes();

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/scheduling-date-time/time/2023-01-01/hair"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[\"15:00\",\"15:30\",\"16:30\",\"18:00\",\"18:30\",\"19:00\",\"19:30\",\"20:30\",\"21:00\"]"));
    }

    @Test
    @DisplayName("Should return available scheduling times for make")
    void getAvailableSchedulingTimesByDayForMakeSucess() throws Exception {
        schedulingDateTimeRepository.deleteAll();
        this.addSchedulingDateTimes();

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/scheduling-date-time/time/2023-01-01/make"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[\"18:00\",\"18:30\",\"20:30\",\"21:00\"]"));
    }

    @Test
    @DisplayName("Should return available scheduling times for make and hair")
    void getAvailableSchedulingTimesByDayForMakeHairSucess() throws Exception {
        schedulingDateTimeRepository.deleteAll();
        this.addSchedulingDateTimes();

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/scheduling-date-time/time/2023-01-01/make-hair"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[\"18:00\",\"20:30\",\"21:00\"]"));

        schedulingDateTimeRepository.deleteAll();
    }
}

