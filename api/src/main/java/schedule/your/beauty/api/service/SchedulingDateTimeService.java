package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

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

    public SchedulingDateTime confirmSchedulingDateTime(String dateTime) {
        SchedulingDateTime schedulingDateTime = schedulingDateTimeRepository.findDateTime(dateTime);
        schedulingDateTime.setAvailable(false);

        return schedulingDateTime;
    }
}
