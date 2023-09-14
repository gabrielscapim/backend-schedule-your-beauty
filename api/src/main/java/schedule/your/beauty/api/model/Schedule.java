package schedule.your.beauty.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "schedules")
@Entity(name = "schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @ManyToOne
  @JoinColumn(name = "production_id", nullable = false)
  private Production production;

  @ManyToMany(
    fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinTable(
    name = "scheduled_times",
    joinColumns = { @JoinColumn(name = "schedule_id") },
    inverseJoinColumns = { @JoinColumn(name = "scheduling_time_id") }
  )
  private List<SchedulingTime> schedulingTimes;

  @Column(name = "event_type")
  private String eventType;
}
