package schedule.your.beauty.api.unit.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Testcontainers
@Sql(scripts = "/create_scheduling_date_times_db.sql")
public class SchedulingDateTimeRepositoryTests {

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
  EntityManager entityManager;

  @Autowired
  SchedulingDateTimeRepository schedulingDateTimeRepository;

  String[] dateTimes = {
    "2023-10-01 11:00:00",
    "2023-10-01 11:30:00",
    "2023-10-01 12:00:00",
    "2023-10-01 12:30:00",
  };

  @Test
  @Transactional
  void findAvailableSchedulingTimesByDayForMakeSucess() {
    this.addArrayOfSchedulingDateTimes(dateTimes);
    List<String> foundedSchedulingDateTimes = this
      .schedulingDateTimeRepository
      .findAvailableSchedulingTimesByDayForMake("2023-10-01");
    List<String> expectedTimes = Arrays.asList("11:00", "11:30");

    assertThat(foundedSchedulingDateTimes).isEqualTo(expectedTimes);
  }

  @Test
  @Transactional
  void findAvailableSchedulingTimesByDayForHairSucess() {
    this.addArrayOfSchedulingDateTimes(dateTimes);
    List<String> foundedSchedulingDateTimes = this
      .schedulingDateTimeRepository
      .findAvailableSchedulingTimesByDayForHair("2023-10-01");
    List<String> expectedTimes = Arrays.asList("11:00", "11:30", "12:00", "12:30");

    assertThat(foundedSchedulingDateTimes).isEqualTo(expectedTimes);
  }

  @Test
  @Transactional
  void findAvailableSchedulingTimesByDayForMakeHairSucess() {
    this.addArrayOfSchedulingDateTimes(dateTimes);
    List<String> foundedSchedulingDateTimes = this
      .schedulingDateTimeRepository
      .findAvailableSchedulingTimesByDayForMakeHair("2023-10-01");
    List<String> expectedTimes = Arrays.asList("11:00");

    assertThat(foundedSchedulingDateTimes).isEqualTo(expectedTimes);
  }

  @Test
  @Transactional
  void findDateTimesForHairSucess() {
    this.addArrayOfSchedulingDateTimes(dateTimes);
    List<SchedulingDateTime> foundedSchedulingDateTimes = this
      .schedulingDateTimeRepository.findDateTimesForHair("2023-10-01 11:00");

    assertThat(foundedSchedulingDateTimes.get(0).getDateTime())
      .isEqualTo("2023-10-01 11:00:00");
    assertThat(foundedSchedulingDateTimes.size()).isEqualTo(1);
  }

  @Test
  @Transactional
  void findDateTimesForMakeSucess() {
    this.addArrayOfSchedulingDateTimes(dateTimes);
    List<SchedulingDateTime> foundedSchedulingDateTimes = this
      .schedulingDateTimeRepository.findDateTimesForMake("2023-10-01 11:00");

    assertThat(foundedSchedulingDateTimes.get(0).getDateTime())
      .isEqualTo("2023-10-01 11:00:00");
    assertThat(foundedSchedulingDateTimes.get(1).getDateTime())
      .isEqualTo("2023-10-01 11:30:00");
    assertThat(foundedSchedulingDateTimes.get(2).getDateTime())
      .isEqualTo("2023-10-01 12:00:00");
    assertThat(foundedSchedulingDateTimes.size()).isEqualTo(3);
  }

  @Test
  @Transactional
  void findDateTimesForMakeFailed() {
    this.addArrayOfSchedulingDateTimes(dateTimes);
    List<SchedulingDateTime> foundedSchedulingDateTimes = this
      .schedulingDateTimeRepository.findDateTimesForMake("2023-10-02 11:00");

    assertThat(foundedSchedulingDateTimes.size()).isEqualTo(0);
  }

  @Test
  @Transactional
  void deleteAvailableSchedulingDateTimesByDateSucess() {
    this.addSchedulingDateTime("2023-10-01 11:00:00", false, true);
    SchedulingDateTime schedulingDateTimeFromRepository = this
      .schedulingDateTimeRepository
      .findDateTimesForHair("2023-10-01 11:00:00")
      .get(0);

    assertThat(schedulingDateTimeFromRepository.getDateTime()).isEqualTo("2023-10-01 11:00:00");

    this.schedulingDateTimeRepository.deleteAvailableSchedulingDateTimesByDate("2023-10-01 11:00:00");

    List<SchedulingDateTime> schedulingDateTimeFromRepositoryAfterDelete = this
      .schedulingDateTimeRepository
      .findDateTimesForHair("2023-10-01 11:00:00");

    assertThat(schedulingDateTimeFromRepositoryAfterDelete.size()).isEqualTo(0);
  }

  @Test
  @Transactional
  void findNotAvailableLastTimeToScheduleByDateSucess() {
    this.addSchedulingDateTime("2023-10-01 11:00:00", true, false);
    SchedulingDateTime schedulingDateTimeFromRepository = this
      .schedulingDateTimeRepository
      .findNotAvailableLastTimeToScheduleByDate("2023-10-01");

    assertThat(schedulingDateTimeFromRepository.getDateTime()).isEqualTo("2023-10-01 11:00:00");
  }

  void addArrayOfSchedulingDateTimes(String[] dateTimes) {
    for (String dateTime: dateTimes) {
      this.addSchedulingDateTime(dateTime, false, true);
    }
  }

  void addSchedulingDateTime(String dateTime, boolean lastScheduleTimeDay, boolean available) {
    SchedulingDateTime newSchedulingDateTime = new SchedulingDateTime(
      dateTime,
      lastScheduleTimeDay,
      available
    );

    this.entityManager.persist(newSchedulingDateTime);
  }
}
