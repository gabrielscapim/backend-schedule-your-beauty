package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import schedule.your.beauty.api.model.SchedulingTime;

public interface SchedulingTimeRepository extends JpaRepository<SchedulingTime, Integer> {
}
