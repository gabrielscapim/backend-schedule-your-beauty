package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import schedule.your.beauty.api.model.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
  List<Schedule> findBySchedulingDateTimes_DateTimeStartingWith(String date);
}
