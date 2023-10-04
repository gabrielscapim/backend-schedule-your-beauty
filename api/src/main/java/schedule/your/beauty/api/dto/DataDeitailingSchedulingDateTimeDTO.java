package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.SchedulingDateTime;

public record DataDeitailingSchedulingDateTimeDTO(
  int id,
  String time,
  boolean lastScheduleTimeDay,
  boolean available


) {
  public DataDeitailingSchedulingDateTimeDTO(SchedulingDateTime schedulingDateTime) {
    this(
      schedulingDateTime.getId(),
      getScheduleTime(schedulingDateTime),
      schedulingDateTime.isLastScheduleTimeDay(),
      schedulingDateTime.isAvailable()
    );
  }

  static String getScheduleTime(SchedulingDateTime schedulingDateTime) {
    String[] timeArray = schedulingDateTime.getDateTime().split(" ")[1].split(":");
    return timeArray[0] + ":" + timeArray[1];
  }
}
