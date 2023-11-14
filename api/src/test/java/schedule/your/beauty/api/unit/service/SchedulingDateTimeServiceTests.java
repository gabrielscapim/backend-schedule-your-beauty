package schedule.your.beauty.api.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import schedule.your.beauty.api.exceptions.NotAvailableDateTimeException;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;
import schedule.your.beauty.api.service.SchedulingDateTimeService;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@Sql(scripts = "/sql/create_scheduling_date_times_db.sql")
public class SchedulingDateTimeServiceTests {

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
  @Spy
  private SchedulingDateTimeService schedulingDateTimeService;

  @Mock
  private SchedulingDateTimeRepository schedulingDateTimeRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    doNothing().when(schedulingDateTimeRepository).deleteAvailableSchedulingDateTimesByDate(any(String.class));
  }

  @Test
  @DisplayName("Should return NotAvailableDateTimeException because the return of repository is an empty array")
  void confirmSchedulingDateTimeForHairFail() {
    when(schedulingDateTimeRepository
        .findDateTimesForHair("2023-01-01 14:00:00"))
        .thenReturn(createSchedulingDateTimeList(0, true));

    Assertions.assertThrows(NotAvailableDateTimeException.class, () -> {
      schedulingDateTimeService.confirmSchedulingDateTimeForHair(
        "2023-01-01 14:00:00"
      );
    });
  }

  @Test
  @DisplayName("Should return NotAvailableDateTimeException because the return of repository is an empty array")
  void confirmSchedulingDateTimeForMakeFail() {
    when(schedulingDateTimeRepository
      .findDateTimesForMake("2023-01-01 14:00:00"))
      .thenReturn(createSchedulingDateTimeList(0, true));

    Assertions.assertThrows(NotAvailableDateTimeException.class, () -> {
      schedulingDateTimeService.confirmSchedulingDateTimeForMake(
        "2023-01-01 14:00:00"
      );
    });
  }

  @Test
  @DisplayName("Should return NotAvailableDateTimeException because the return of repository is an empty array")
  void confirmSchedulingDateTimeForMakeHairFail() {
    when(schedulingDateTimeRepository
      .findDateTimesForMakeHair("2023-01-01 14:00:00"))
      .thenReturn(createSchedulingDateTimeList(0, true));

    Assertions.assertThrows(NotAvailableDateTimeException.class, () -> {
      schedulingDateTimeService.confirmSchedulingDateTimeForMakeHair(
        "2023-01-01 14:00:00"
      );
    });
  }

  @Test
  @DisplayName("Should return NotAvailableDateTimeException because SchedulingDateTime is not available")
  void confirmSchedulingDateTimeFail() {
    when(schedulingDateTimeRepository
      .findDateTimesForMakeHair("2023-01-01 14:00:00"))
      .thenReturn(createSchedulingDateTimeList(1, false));

    Assertions.assertThrows(NotAvailableDateTimeException.class, () -> {
      schedulingDateTimeService.confirmSchedulingDateTimeForMakeHair(
        "2023-01-01 14:00:00"
      );
    });
  }

  @Test
  @DisplayName("Should return a list of SchedulingDateTime")
  void confirmSchedulingDateTimeSucess() {
    SchedulingDateTimeService schedulingDateTimeServiceMock = new SchedulingDateTimeService();
    SchedulingDateTimeService schedulingDateTimeServiceSpy = Mockito.spy(schedulingDateTimeServiceMock);

    when(schedulingDateTimeRepository
      .findDateTimesForHair("2023-01-01 14:00:00"))
      .thenReturn(createSchedulingDateTimeList(1, false));

    Mockito.doReturn(createSchedulingDateTimeList(1, true))
      .when(schedulingDateTimeServiceSpy)
      .confirmSchedulingDateTimeForHair("2023-01-01 14:00:00");

    List<SchedulingDateTime> schedulingDateTimesFromService = schedulingDateTimeServiceSpy
      .confirmSchedulingDateTime("2023-01-01 14:00:00", "Penteado");

    Assertions.assertEquals(
      schedulingDateTimesFromService.get(0).getDateTime(),
      "2023-01-01 14:00:00");
    Assertions.assertFalse(schedulingDateTimesFromService.get(0).isAvailable());
  }

  private static List<SchedulingDateTime> createSchedulingDateTimeList(int listSize, boolean available) {
    List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();
    for (int index = 0; index < listSize; index += 1) {
      schedulingDateTimes.add(new SchedulingDateTime(
        "2023-01-01 14:00:00",
        false,
        available
      ));
    }

    return schedulingDateTimes;
  }
}
