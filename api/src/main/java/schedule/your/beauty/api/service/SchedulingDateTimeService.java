package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DefaultErrorDTO;
import schedule.your.beauty.api.exceptions.ApplicationExceptionHandler;
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

    @Autowired
    private ApplicationExceptionHandler applicationExceptionHandler;

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

        if (schedulingDateTimes.size() == 0) {
            throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
        }

        for (SchedulingDateTime schedulingDateTime : schedulingDateTimes) {
            if (!schedulingDateTime.isAvailable()) {
                throw new NotAvailableDateTimeException("A data de agendamento deve estar disponível");
            }
            schedulingDateTime.setAvailable(false);
        }

        return schedulingDateTimes;
    }
}
