package schedule.your.beauty.api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataAddSchedulingDateTimeDTO;
import schedule.your.beauty.api.dto.DataDeitailingSchedulingDateTimeDTO;
import schedule.your.beauty.api.dto.DefaultErrorDTO;
import schedule.your.beauty.api.exceptions.ApplicationExceptionHandler;
import schedule.your.beauty.api.exceptions.NotAvailableDateTimeException;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SchedulingDateTimeService {

    @Autowired
    private SchedulingDateTimeRepository schedulingDateTimeRepository;

    @Autowired
    private ApplicationExceptionHandler applicationExceptionHandler;

    public Iterable<DataDeitailingSchedulingDateTimeDTO> getSchedulingTimesByDay(String day) {
        var schedulingDateTimesFromRepository = schedulingDateTimeRepository.findByDateTimeStartingWith(day);
        ArrayList<DataDeitailingSchedulingDateTimeDTO> schedulingDateTimes = new ArrayList<>();

        schedulingDateTimesFromRepository.forEach(schedulingDateTime -> schedulingDateTimes.add(new DataDeitailingSchedulingDateTimeDTO(schedulingDateTime)));

        return schedulingDateTimes;
    }


    public Iterable<String> getAvailableSchedulingTimesByDay(String day, String production) {
        if (Objects.equals(production, "make")) {
            return schedulingDateTimeRepository.findAvailableSchedulingTimesByDayForMake(day);
        }
        if (Objects.equals(production, "make-hair")) {
            return schedulingDateTimeRepository.findAvailableSchedulingTimesByDayForMakeHair(day);
        }
        return schedulingDateTimeRepository.findAvailableSchedulingTimesByDayForHair(day);
    }

    public Iterable<String> getSchedulingDates() {
        return schedulingDateTimeRepository.findSchedulingDates();
    }

    private List<SchedulingDateTime> confirmSchedulingDateTimeForHair(
      List<SchedulingDateTime> schedulingDateTimes,
      String dateTime
      ) {
        List<SchedulingDateTime> schedulingDateTimesFromRepository = schedulingDateTimeRepository.findDateTimesForHair(dateTime);

        if (schedulingDateTimesFromRepository.size() != 1) {
            throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
        }

        return schedulingDateTimesFromRepository;
    }

    private List<SchedulingDateTime> confirmSchedulingDateTimeForMake(
      List<SchedulingDateTime> schedulingDateTimes,
      String dateTime
    ) {
        List<SchedulingDateTime> schedulingDateTimesFromRepository = schedulingDateTimeRepository.findDateTimesForMake(dateTime);

        if (schedulingDateTimesFromRepository.size() != 3) {
            throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
        }

        return schedulingDateTimesFromRepository;
    }

    private List<SchedulingDateTime> confirmSchedulingDateTimeForMakeHair(
      List<SchedulingDateTime> schedulingDateTimes,
      String dateTime
    ) {
        List<SchedulingDateTime> schedulingDateTimesFromRepository = schedulingDateTimeRepository.findDateTimesForMakeHair(dateTime);

        if (schedulingDateTimesFromRepository.size() != 4) {
            throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
        }

        return schedulingDateTimesFromRepository;
    }

    public List<SchedulingDateTime> confirmSchedulingDateTime(String dateTime, String productionName) {
        List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();

        if (Objects.equals(productionName, "Penteado")) {
            schedulingDateTimes = this.confirmSchedulingDateTimeForHair(schedulingDateTimes, dateTime);
        }
        if (Objects.equals(productionName, "Maquiagem")) {
            schedulingDateTimes = this.confirmSchedulingDateTimeForMake(schedulingDateTimes, dateTime);
        }
        if (Objects.equals(productionName, "Maquiagem e Penteado")) {
            schedulingDateTimes = this.confirmSchedulingDateTimeForMakeHair(schedulingDateTimes, dateTime);
        }

        for (SchedulingDateTime schedulingDateTime : schedulingDateTimes) {
            if (!schedulingDateTime.isAvailable()) {
                throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
            }
            schedulingDateTime.setAvailable(false);
        }

        return schedulingDateTimes;
    }

    @Transactional
    public void addSchedulingDateTimesByDate(
      DataAddSchedulingDateTimeDTO dataAddSchedulingDateTimeDTO,
      String date) {
        var notAvailableLastTimeToSchedule = schedulingDateTimeRepository.findNotAvailableLastTimeToScheduleByDate(date);
        schedulingDateTimeRepository.deleteAvailableSchedulingDateTimesByDate(date);
        System.out.println(notAvailableLastTimeToSchedule);

        dataAddSchedulingDateTimeDTO.times().forEach((time) -> {
            SchedulingDateTime schedulingDateTime = new SchedulingDateTime(
              date + " " + time + ":00",
              false,
              true
            );

            schedulingDateTimeRepository.save(schedulingDateTime);
        });

        if (dataAddSchedulingDateTimeDTO.lastTimeToSchedule() != null
        && notAvailableLastTimeToSchedule == null) {
            this.addLastSchedulingDateTimeOnDay(dataAddSchedulingDateTimeDTO, date);
        }
    }

    public void addLastSchedulingDateTimeOnDay(
      DataAddSchedulingDateTimeDTO dataAddSchedulingDateTimeDTO,
      String date
    ) {
        SchedulingDateTime schedulingDateTime = new SchedulingDateTime(
          date + " " + dataAddSchedulingDateTimeDTO.lastTimeToSchedule() + ":00",
          true,
          true
        );

        schedulingDateTimeRepository.save(schedulingDateTime);
    }
}
