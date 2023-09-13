package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingTime;

public record DataDeitailingScheduleDTO(
        int id,
        Client client,
        Production production,
        SchedulingTime schedulingTime,
        String eventType
) {
    public DataDeitailingScheduleDTO(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getClient(),
                schedule.getProduction(),
                schedule.getSchedulingTime(),
                schedule.getEventType()
        );
    }
}
