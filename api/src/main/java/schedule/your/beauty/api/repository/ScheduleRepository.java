package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import schedule.your.beauty.api.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
