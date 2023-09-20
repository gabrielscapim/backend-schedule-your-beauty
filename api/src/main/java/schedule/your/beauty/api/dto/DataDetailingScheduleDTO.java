package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingDateTime;

import java.util.List;

public record DataDetailingScheduleDTO(
        int id,
        Client client,
        Production production,
        List<SchedulingDateTime> schedulingDateTimes,
        String eventName
) {
    public DataDetailingScheduleDTO(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getClient(),
                schedule.getProduction(),
                schedule.getSchedulingDateTimes(),
                schedule.getEventName()
        );
    }
}
