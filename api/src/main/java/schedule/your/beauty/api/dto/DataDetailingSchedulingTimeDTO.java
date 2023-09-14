package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.SchedulingTime;

public record DataDetailingSchedulingTimeDTO(
      int id,
      String dateTime,
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
