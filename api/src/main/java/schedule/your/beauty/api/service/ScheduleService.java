package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataAddScheduleDTO;
import schedule.your.beauty.api.dto.DataDetailingScheduleDTO;
import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.ClientRepository;
import schedule.your.beauty.api.repository.ProductionRepository;
import schedule.your.beauty.api.repository.ScheduleRepository;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private SchedulingDateTimeService schedulingDateTimeService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductionRepository productionRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    public Iterable<DataDetailingScheduleDTO> getAllSchedules() {
        var schedulesFromRepository = scheduleRepository.findAll();
        ArrayList<DataDetailingScheduleDTO> schedules = new ArrayList<>();

        schedulesFromRepository.forEach(schedule -> schedules.add(new DataDetailingScheduleDTO(schedule)));

        return schedules;
    }

    public Schedule addSchedule(DataAddScheduleDTO dataAddScheduleDTO) {
        Client client = clientRepository.findClientByNumber(dataAddScheduleDTO.clientNumber());
        Production production = productionRepository.findProductionByName(dataAddScheduleDTO.productionName());
        List<SchedulingDateTime> schedulingDateTimes = new ArrayList<>();

        if (client == null) {
            client = clientService.addClient(dataAddScheduleDTO.clientName(), dataAddScheduleDTO.clientNumber());
        }

        dataAddScheduleDTO.schedulingDateTimes().forEach(scheduling -> {
            SchedulingDateTime schedulingDateTime = schedulingDateTimeService.confirmSchedulingDateTime(scheduling);
            schedulingDateTimes.add(schedulingDateTime);
        });

        Schedule schedule = new Schedule(
                client,
                production,
                schedulingDateTimes,
                dataAddScheduleDTO.eventName()
        );

        scheduleRepository.save(schedule);

        return schedule;
    }
}
