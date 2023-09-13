package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.SchedulingTime;

import java.util.Date;

public record DataDeitailingSchedulingTimeDTO(
        int id,
        Date dateTime,
        boolean lastScheduleTimeDay,
        boolean available
) {
    public DataDeitailingSchedulingTimeDTO(SchedulingTime schedulingTime) {
        this(
                schedulingTime.getId(),
                schedulingTime.getDateTime(),
                schedulingTime.isLastScheduleTimeDay(),
                schedulingTime.isAvailable()
        );
    }
}
