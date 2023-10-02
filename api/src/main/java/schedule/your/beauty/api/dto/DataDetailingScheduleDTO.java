package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Schedule;
import schedule.your.beauty.api.model.SchedulingDateTime;


public record DataDetailingScheduleDTO(
        int id,
        String clientName,
        String date,
        String startTime,
        String productionName,
        String eventName,
        String clientNumber
) {

    static String getScheduleDate(SchedulingDateTime schedulingDateTime) {
        String[] date = schedulingDateTime.getDateTime().split(" ")[0].split("-");
        return date[2] + "/" + date[1] + "/" + date[0];
    }

    static String getScheduleStartTime(SchedulingDateTime schedulingDateTime) {
        return schedulingDateTime.getDateTime().split(" ")[1];
    }

    public DataDetailingScheduleDTO(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getClient().getName(),
                getScheduleDate(schedule.getSchedulingDateTimes().get(0)),
                getScheduleStartTime(schedule.getSchedulingDateTimes().get(0)),
                schedule.getProduction().getName(),
                schedule.getEventName(),
                schedule.getClient().getNumber()
        );
    }
}
