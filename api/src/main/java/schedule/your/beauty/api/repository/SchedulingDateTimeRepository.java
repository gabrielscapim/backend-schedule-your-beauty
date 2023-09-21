package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import schedule.your.beauty.api.model.SchedulingDateTime;

import java.util.List;

public interface SchedulingDateTimeRepository extends JpaRepository<SchedulingDateTime, Integer> {
  @Query(value = "SELECT DATE_FORMAT(date_time, '%H:%i')\n" +
    "FROM scheduling_date_times t1\n" +
    "WHERE \n" +
    "    DATE(t1.date_time) = :dateTime\n" +
    "    AND (\n" +
    "        (t1.last_schedule_time_day = FALSE \n" +
    "            AND EXISTS (\n" +
    "                SELECT 1\n" +
    "                FROM scheduling_date_times t2\n" +
    "                WHERE t2.date_time = t1.date_time + INTERVAL 30 MINUTE\n" +
    "                AND t2.available = TRUE\n" +
    "            ) \n" +
    "            AND EXISTS (\n" +
    "                SELECT 1\n" +
    "                FROM scheduling_date_times t3\n" +
    "                WHERE t3.date_time = t1.date_time + INTERVAL 60 MINUTE\n" +
    "                AND t3.available = TRUE\n" +
    "            )\n" +
    "        )\n" +
    "        OR t1.last_schedule_time_day = TRUE\n" +
    "        OR EXISTS (\n" +
    "            SELECT 1\n" +
    "            FROM scheduling_date_times t2\n" +
    "            WHERE t2.date_time = t1.date_time + INTERVAL 30 MINUTE\n" +
    "            AND last_schedule_time_day = TRUE\n" +
    "            AND t2.available = TRUE\n" +
    "        )\n" +
    "    )\n" +
    "    AND t1.available = TRUE\n" +
    "ORDER BY t1.date_time;", nativeQuery = true)
  List<String> findAvailableSchedulingTimesByDayForMake(@Param("dateTime") String dateTime);

  @Query(value = "SELECT DATE_FORMAT(date_time, '%H:%i')\n" +
    "FROM scheduling_date_times t1\n" +
    "WHERE \n" +
    "    DATE(t1.date_time) = :dateTime\n" +
    "    AND (\n" +
    "        (t1.last_schedule_time_day = FALSE \n" +
    "            AND EXISTS (\n" +
    "                SELECT 1\n" +
    "                FROM scheduling_date_times t2\n" +
    "                WHERE t2.date_time = t1.date_time + INTERVAL 30 MINUTE\n" +
    "                AND t2.available = TRUE\n" +
    "            ) \n" +
    "            AND EXISTS (\n" +
    "                SELECT 1\n" +
    "                FROM scheduling_date_times t3\n" +
    "                WHERE t3.date_time = t1.date_time + INTERVAL 60 MINUTE\n" +
    "                AND t3.available = TRUE\n" +
    "            )\n" +
    "            AND EXISTS (\n" +
    "                SELECT 1\n" +
    "                FROM scheduling_date_times t4\n" +
    "                WHERE t4.date_time = t1.date_time + INTERVAL 90 MINUTE\n" +
    "                AND t4.available = TRUE\n" +
    "            )\n" +
    "        )\n" +
    "        OR t1.last_schedule_time_day = TRUE\n" +
    "        OR EXISTS (\n" +
    "            SELECT 1\n" +
    "            FROM scheduling_date_times t2\n" +
    "            WHERE t2.date_time = t1.date_time + INTERVAL 30 MINUTE\n" +
    "            AND last_schedule_time_day = TRUE\n" +
    "            AND t2.available = TRUE\n" +
    "        )\n" +
    "        OR EXISTS (\n" +
    "            SELECT 1\n" +
    "            FROM scheduling_date_times t3\n" +
    "            WHERE t3.date_time = t1.date_time + INTERVAL 60 MINUTE\n" +
    "            AND last_schedule_time_day = TRUE\n" +
    "            AND t3.available = TRUE\n" +
    "        )\n" +
    "    )\n" +
    "    AND t1.available = TRUE\n" +
    "ORDER BY t1.date_time;", nativeQuery = true)
  List<String> findAvailableSchedulingTimesByDayForMakeHair(@Param("dateTime") String dateTime);

  @Query(value = "SELECT DATE_FORMAT(date_time, '%H:%i')\n" +
    "FROM scheduling_date_times t1\n" +
    "WHERE \n" +
    "    DATE(t1.date_time) = :dateTime\n" +
    "    AND t1.available = TRUE\n" +
    "    ORDER BY t1.date_time;\n", nativeQuery = true)
  List<String> findAvailableSchedulingTimesByDayForHair(@Param("dateTime") String dateTime);

  @Query(value = "SELECT DISTINCT DATE(date_time) \n" +
          "FROM scheduling_date_times\n" +
          "WHERE available = true\n" +
          "ORDER BY DATE(date_time)", nativeQuery = true)
  List<String> findSchedulingDates();


  @Query(value = "SELECT \n" +
          "    id, date_time, last_schedule_time_day, available\n" +
          "FROM\n" +
          "    scheduling_date_times\n" +
          "WHERE\n" +
          "    date_time = :dateTime\n" +
          "    AND available = true", nativeQuery = true)
  List<SchedulingDateTime> findDateTimesForHair(@Param("dateTime") String dateTime);

  @Query(value = "SELECT \n" +
          "    id, date_time, last_schedule_time_day, available\n" +
          "FROM\n" +
          "    scheduling_date_times\n" +
          "WHERE\n" +
          "    date_time = :dateTime\n" +
          "        OR date_time = :dateTime + INTERVAL 30 MINUTE\n" +
          "        OR date_time = :dateTime + INTERVAL 60 MINUTE" +
          "    AND available = true", nativeQuery = true)
  List<SchedulingDateTime> findDateTimesForMake(@Param("dateTime") String dateTime);

  @Query(value = "SELECT \n" +
          "    id, date_time, last_schedule_time_day, available\n" +
          "FROM\n" +
          "    scheduling_date_times\n" +
          "WHERE\n" +
          "    date_time = :dateTime\n" +
          "        OR date_time = :dateTime + INTERVAL 30 MINUTE\n" +
          "        OR date_time = :dateTime + INTERVAL 60 MINUTE" +
          "        OR date_time = :dateTime + INTERVAL 90 MINUTE" +
          "    AND available = true", nativeQuery = true)
  List<SchedulingDateTime> findDateTimesForMakeHair(@Param("dateTime") String dateTime);
}
