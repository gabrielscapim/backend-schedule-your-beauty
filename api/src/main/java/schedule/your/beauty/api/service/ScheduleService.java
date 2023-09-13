package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataDeitailingScheduleDTO;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingTime;
import schedule.your.beauty.api.repository.ScheduleRepository;

import java.util.ArrayList;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Iterable<DataDeitailingScheduleDTO> getAllSchedules() {
        var schedulesFromRepository = scheduleRepository.findAll();
        ArrayList<DataDeitailingScheduleDTO> schedules = new ArrayList<>();

        schedulesFromRepository.forEach(schedule -> schedules.add(new DataDeitailingScheduleDTO(schedule)));

        return schedules;
    }

}
