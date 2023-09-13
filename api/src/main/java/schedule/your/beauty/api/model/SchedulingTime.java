package schedule.your.beauty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "scheduling_times")
@Entity(name = "scheduling_time")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SchedulingTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "date_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp dateTime;

  @Column(name = "last_schedule_time_day")
  private boolean lastScheduleTimeDay;

  @Column(name = "available")
  private boolean available;

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
    },
    mappedBy = "schedulingTimes")
  @JsonIgnore
  private List<Schedule> schedules;

}
