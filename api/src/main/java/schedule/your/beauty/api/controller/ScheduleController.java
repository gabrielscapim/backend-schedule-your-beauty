package schedule.your.beauty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import schedule.your.beauty.api.dto.DataAddScheduleDTO;
import schedule.your.beauty.api.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @PostMapping
    public ResponseEntity addSchedule(@RequestBody DataAddScheduleDTO dataAddScheduleDTO, UriComponentsBuilder uriComponentsBuilder) {
        var schedule = scheduleService.addSchedule(dataAddScheduleDTO);

        var URI = uriComponentsBuilder
                .path("/schedule")
                .buildAndExpand(schedule.getId()).toUri();

        return ResponseEntity.created(URI).body(schedule);
    }
}
