package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import schedule.your.beauty.api.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
  List<Schedule> findBySchedulingDateTimes_DateTimeStartingWith(String date);
}
