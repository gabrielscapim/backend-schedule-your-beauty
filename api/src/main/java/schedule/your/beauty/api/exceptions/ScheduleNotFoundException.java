package schedule.your.beauty.api.exceptions;

public class ScheduleNotFoundException extends RuntimeException {

  public ScheduleNotFoundException(String message) {
    super(message);
  }
}
