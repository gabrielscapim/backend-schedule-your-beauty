package schedule.your.beauty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schedule.your.beauty.api.service.SchedulingDateTimeService;

@RestController
@RequestMapping("scheduling-date-time")
public class SchedulingDateTimeController {

    @Autowired
    private SchedulingDateTimeService schedulingDateTimeService;

    @GetMapping("/time/{day}/{production}")
    public ResponseEntity getAvailableSchedulingTimesByDay(@PathVariable String day, @PathVariable String production) {
        return ResponseEntity.ok(schedulingDateTimeService.getAvailableSchedulingTimesByDay(day, production));
    }

    @GetMapping("/date")
    public ResponseEntity getSchedulingDates() {
        return ResponseEntity.ok(schedulingDateTimeService.getSchedulingDates());
    }
}
