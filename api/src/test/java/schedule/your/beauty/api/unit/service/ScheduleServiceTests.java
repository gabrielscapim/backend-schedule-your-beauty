package schedule.your.beauty.api.unit.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import schedule.your.beauty.api.dto.DataAddScheduleDTO;
import schedule.your.beauty.api.dto.DataDetailingScheduleDTO;
import schedule.your.beauty.api.exceptions.ScheduleNotFoundException;
import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.ClientRepository;
import schedule.your.beauty.api.repository.ProductionRepository;
import schedule.your.beauty.api.repository.ScheduleRepository;
import schedule.your.beauty.api.service.ClientService;
import schedule.your.beauty.api.service.ScheduleService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import schedule.your.beauty.api.service.SchedulingDateTimeService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ScheduleServiceTests {

  @Autowired
  @InjectMocks
  private ScheduleService scheduleService;

  @Mock
  private ScheduleRepository scheduleRepository;

  @Mock
  private SchedulingDateTimeService schedulingDateTimeService;

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private ProductionRepository productionRepository;

  @Mock
  private ClientService clientService;

  @Autowired
  EntityManager entityManager;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should return schedules DTO by day")
  void getSchedulesByDaySucess() {
    List<Schedule> mockSchedules = this.createSchedule();

    // Repository mock
    when(scheduleRepository
      .findBySchedulingDateTimes_DateTimeStartingWith("2023-01-01"))
      .thenReturn(mockSchedules);

    DataDetailingScheduleDTO schedulesDTOFromService = scheduleService
      .getSchedulesByDay("2023-01-01")
      .get(0);

    assertThat(schedulesDTOFromService.date()).isEqualTo("01/01/2023");
    assertThat(schedulesDTOFromService.startTime()).isEqualTo("11:00:00");
    assertThat(schedulesDTOFromService.productionName()).isEqualTo("Penteado");
    assertThat(schedulesDTOFromService.eventName()).isEqualTo("Teste");
    assertThat(schedulesDTOFromService.clientName()).isEqualTo("João");
    assertThat(schedulesDTOFromService.clientNumber()).isEqualTo("99999999999");
  }

  @Test
  @DisplayName("Should return schedule by id")
  void getScheduleByIdSucess() {
    Schedule mockSchedule = this.createSchedule().get(0);

    // Repository mock
    when(scheduleRepository
      .getReferenceById(1))
      .thenReturn(mockSchedule);

    Schedule scheduleFromService = this.scheduleService.getScheduleById(1);

    assertThat(mockSchedule).isEqualTo(scheduleFromService);
  }

  @Test
  @DisplayName("Should return an error")
  void getScheduleByIdFail() {
    // Repository mock
    when(scheduleRepository
      .getReferenceById(1))
      .thenReturn(null);

    Assertions.assertThrows(ScheduleNotFoundException.class, () -> {
      scheduleService.getScheduleById(1);
    });
  }

  @Test
  @DisplayName("Should delete schedule with sucess")
  void deleteScheduleSucess() {

    int id = 1;
    Schedule mockSchedule = this.createSchedule().get(0);
    when(scheduleRepository.getReferenceById(id)).thenReturn(mockSchedule);

    scheduleService.deleteSchedule(id);

    // Verify if deleteById from scheduleRepository has been called
    verify(scheduleRepository, times(1)).deleteById(id);

    mockSchedule.getSchedulingDateTimes().forEach(schedulingDateTime -> {
      assertThat(schedulingDateTime.isAvailable()).isEqualTo(true);
    });
  }

  @Test
  @DisplayName("Should return schedule after adding")
  void addScheduleSucess() {
    DataAddScheduleDTO dataAddScheduleDTO = new DataAddScheduleDTO(
      "João",
      "99999999999",
      "Penteado",
      "Formatura",
      "2023-01-01 16:00"
    );
    SchedulingDateTime schedulingDateTime = new SchedulingDateTime(
      "2023-01-01 16:00:00",
      false,
      true
    );
    List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();
    schedulingDateTimes.add(schedulingDateTime);
    Client client = new Client(
      "João",
      "99999999999"
    );
    Production production = new Production(
      "Penteado",
      150
    );
    Schedule expectedSchedule = this.createSchedule().get(0);

    when(schedulingDateTimeService
      .confirmSchedulingDateTime(
        dataAddScheduleDTO.schedulingDateTime(),
        dataAddScheduleDTO.productionName()))
      .thenReturn(schedulingDateTimes);
    when(clientRepository
      .findClientByNumber(dataAddScheduleDTO.clientNumber()))
      .thenReturn(client);
    when(productionRepository
      .findProductionByName(dataAddScheduleDTO.productionName()))
      .thenReturn(production);

    Schedule scheduleFromService = scheduleService.addSchedule(dataAddScheduleDTO);

    assertThat(scheduleFromService).isEqualTo(expectedSchedule);
  }

  private List<Schedule> createSchedule() {
    List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();
    schedulingDateTimes.add(new SchedulingDateTime(
      "2023-01-01 11:00:00",
      false,
      true
    ));

    List<Schedule> schedules = new ArrayList<>();
    schedules.add(new Schedule(
      new Client("João", "99999999999"),
      new Production("Penteado", 150),
      schedulingDateTimes,
      "Teste"
    ));

    return schedules;
  }
}
