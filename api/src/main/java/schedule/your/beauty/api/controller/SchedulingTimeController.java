package schedule.your.beauty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import schedule.your.beauty.api.service.SchedulingTimeService;

@RestController
@RequestMapping("scheduling-time")
public class SchedulingTimeController {

    @Autowired
    private SchedulingTimeService schedulingTimeService;

    @GetMapping
    public ResponseEntity getAllSchedulingTimes() {
        return ResponseEntity.ok(schedulingTimeService.getAllSchedulingTimes());
    }
}
