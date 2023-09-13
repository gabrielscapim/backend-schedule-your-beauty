package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.SchedulingTime;

import java.sql.Timestamp;
import java.util.Date;

public record DataDetailingSchedulingTimeDTO(
        int id,
        Timestamp dateTime,
        boolean lastScheduleTimeDay,
        boolean available
) {
    public DataDetailingSchedulingTimeDTO(SchedulingTime schedulingTime) {
        this(
                schedulingTime.getId(),
                schedulingTime.getDateTime(),
                schedulingTime.isLastScheduleTimeDay(),
                schedulingTime.isAvailable()
        );
    }
}
