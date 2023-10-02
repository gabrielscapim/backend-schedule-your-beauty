package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataAddScheduleDTO;
import schedule.your.beauty.api.dto.DataDetailingScheduleDTO;
import schedule.your.beauty.api.exceptions.NotAvailableDateTimeException;
import schedule.your.beauty.api.exceptions.ScheduleNotFoundException;
import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingDateTime;
import schedule.your.beauty.api.repository.ClientRepository;
import schedule.your.beauty.api.repository.ProductionRepository;
import schedule.your.beauty.api.repository.ScheduleRepository;
import schedule.your.beauty.api.repository.SchedulingDateTimeRepository;

import java.time.LocalDate;
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

    public Iterable<DataDetailingScheduleDTO> getSchedulesByDay(String date) {
        var schedulesFromRepository = scheduleRepository.findBySchedulingDateTimes_DateTimeStartingWith(date);
        ArrayList<DataDetailingScheduleDTO> schedules = new ArrayList<>();

        schedulesFromRepository.forEach(schedule -> schedules.add(new DataDetailingScheduleDTO(schedule)));

        return schedules;
    }

    public Schedule getScheduleById(int id) {
        var schedule = scheduleRepository.getReferenceById(id);

        if (schedule == null) {
            throw new ScheduleNotFoundException("O agendamento não foi encontrado");
        }

        return schedule;
    }

    public void deleteSchedule(int id) {
        var schedule = this.getScheduleById(id);

        schedule.getSchedulingDateTimes().forEach(schedulingDateTime -> schedulingDateTime.setAvailable(true));

        scheduleRepository.deleteById(id);
    }

    public Schedule addSchedule(DataAddScheduleDTO dataAddScheduleDTO) {
        List<SchedulingDateTime> schedulingDateTimes = schedulingDateTimeService
          .confirmSchedulingDateTime(dataAddScheduleDTO.schedulingDateTime(), dataAddScheduleDTO.productionName());
        Client client = clientRepository.findClientByNumber(dataAddScheduleDTO.clientNumber());
        Production production = productionRepository.findProductionByName(dataAddScheduleDTO.productionName());

        if (client == null) {
            client = clientService.addClient(dataAddScheduleDTO.clientName(), dataAddScheduleDTO.clientNumber());
        }

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
