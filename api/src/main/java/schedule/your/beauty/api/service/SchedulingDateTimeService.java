package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SchedulingDateTimeService {

    @Autowired
    private SchedulingDateTimeRepository schedulingDateTimeRepository;

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

        schedulingDateTimes.forEach(schedulingDateTime -> schedulingDateTime.setAvailable(false));

        return schedulingDateTimes;
    }
}
