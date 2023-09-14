package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.SchedulingDateTime;

public record DataDetailingSchedulingDateTimeDTO(
      int id,
      String dateTime,
      boolean lastScheduleTimeDay,
      boolean available
) {
    public DataDetailingSchedulingDateTimeDTO(SchedulingDateTime schedulingDateTime) {
        this(
          schedulingDateTime.getId(),
          schedulingDateTime.getDateTime(),
          schedulingDateTime.isLastScheduleTimeDay(),
          schedulingDateTime.isAvailable()
        );
    }
}
