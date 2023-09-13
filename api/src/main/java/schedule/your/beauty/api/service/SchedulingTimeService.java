package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataDetailingSchedulingTimeDTO;
import schedule.your.beauty.api.repository.SchedulingTimeRepository;

import java.util.ArrayList;

@Service
public class SchedulingTimeService {

    @Autowired
    private SchedulingTimeRepository schedulingTimeRepository;

    public Iterable<DataDetailingSchedulingTimeDTO> getAllSchedulingTimes() {
        var schedulingTimesFromRepository = schedulingTimeRepository.findAll();
        ArrayList<DataDetailingSchedulingTimeDTO> schedulingTimes = new ArrayList<>();

        schedulingTimesFromRepository.forEach(schedulingTime -> schedulingTimes.add(new DataDetailingSchedulingTimeDTO(schedulingTime)));

        return schedulingTimes;
    }
}
