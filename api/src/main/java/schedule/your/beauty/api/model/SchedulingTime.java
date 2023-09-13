package schedule.your.beauty.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Table(name = "scheduling_times")
@Entity(name = "schedule_time")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SchedulingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_time")
    private Date dateTime;

    @Column(name = "last_schedule_time_day")
    private boolean lastScheduleTimeDay;

    @Column(name = "available")
    private boolean available;

    @OneToMany
    private Set<Schedule> schedules;
}
