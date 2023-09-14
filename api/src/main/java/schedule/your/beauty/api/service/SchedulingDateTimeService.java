package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

@Service
public class SchedulingDateTimeService {

    @Autowired
    private SchedulingDateTimeRepository schedulingDateTimeRepository;

    public Iterable<String> getAvailableSchedulingTimesForDay(String day) {
        return schedulingDateTimeRepository.findAvailableSchedulingTimesForDay(day);
    }
}
