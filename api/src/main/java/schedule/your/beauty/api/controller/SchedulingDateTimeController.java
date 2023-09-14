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

    @GetMapping("/date/{day}")
    public ResponseEntity getAvailableSchedulingTimesForDay(@PathVariable String day) {
        return ResponseEntity.ok(schedulingDateTimeService.getAvailableSchedulingTimesForDay(day));
    }
}
