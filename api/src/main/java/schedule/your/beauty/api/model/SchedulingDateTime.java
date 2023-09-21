package schedule.your.beauty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "scheduling_date_times")
@Entity(name = "scheduling_date_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SchedulingDateTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "date_time")
  private String dateTime;

  @Column(name = "last_schedule_time_day")
  private boolean lastScheduleTimeDay;

  @Column(name = "available")
  private boolean available;

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
    },
    mappedBy = "schedulingDateTimes")
  @JsonIgnore
  private List<Schedule> schedules;

}
