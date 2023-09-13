package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataDetailingScheduleDTO;
import schedule.your.beauty.api.repository.ScheduleRepository;

import java.util.ArrayList;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Iterable<DataDetailingScheduleDTO> getAllSchedules() {
        var schedulesFromRepository = scheduleRepository.findAll();
        ArrayList<DataDetailingScheduleDTO> schedules = new ArrayList<>();

        schedulesFromRepository.forEach(schedule -> schedules.add(new DataDetailingScheduleDTO(schedule)));

        return schedules;
    }

}
