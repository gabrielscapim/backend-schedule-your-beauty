package schedule.your.beauty.api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataAddSchedulingDateTimeDTO;
import schedule.your.beauty.api.dto.DataDeitailingSchedulingDateTimeDTO;
import schedule.your.beauty.api.exceptions.NotAvailableDateTimeException;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SchedulingDateTimeService {

    @Autowired
    private SchedulingDateTimeRepository schedulingDateTimeRepository;

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

    public List<SchedulingDateTime> confirmSchedulingDateTimeForHair(String dateTime) {
        List<SchedulingDateTime> schedulingDateTimesFromRepository = schedulingDateTimeRepository.findDateTimesForHair(dateTime);

        return schedulingDateTimesFromRepository;
    }

    public List<SchedulingDateTime> confirmSchedulingDateTimeForMake(String dateTime) {
        List<SchedulingDateTime> schedulingDateTimesFromRepository = schedulingDateTimeRepository.findDateTimesForMake(dateTime);

        return schedulingDateTimesFromRepository;
    }

    public List<SchedulingDateTime> confirmSchedulingDateTimeForMakeHair(String dateTime) {
        List<SchedulingDateTime> schedulingDateTimesFromRepository = schedulingDateTimeRepository.findDateTimesForMakeHair(dateTime);

        return schedulingDateTimesFromRepository;
    }

    public List<SchedulingDateTime> confirmSchedulingDateTime(String dateTime, String productionName) {
        List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();

        if (Objects.equals(productionName, "Penteado")) {
            schedulingDateTimes = schedulingDateTimeRepository.findDateTimesForHair(dateTime);
        }
        if (Objects.equals(productionName, "Maquiagem")) {
            schedulingDateTimes = schedulingDateTimeRepository.findDateTimesForMake(dateTime);
        }
        if (Objects.equals(productionName, "Maquiagem e Penteado")) {
            schedulingDateTimes = schedulingDateTimeRepository.findDateTimesForMakeHair(dateTime);
        }

        for (SchedulingDateTime schedulingDateTime : schedulingDateTimes) {
            // if (!schedulingDateTime.isAvailable()) {
            //     throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
            // }
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
