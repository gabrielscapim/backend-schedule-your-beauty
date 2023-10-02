package schedule.your.beauty.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{date}")
    public ResponseEntity getSchedulesByDay(@PathVariable String date) {
        return ResponseEntity.ok(scheduleService.getSchedulesByDay(date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSchedule(@PathVariable int id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity addSchedule(@RequestBody @Valid DataAddScheduleDTO dataAddScheduleDTO, UriComponentsBuilder uriComponentsBuilder) {
        var schedule = scheduleService.addSchedule(dataAddScheduleDTO);

        var URI = uriComponentsBuilder
                .path("/schedule")
                .buildAndExpand(schedule.getId()).toUri();

        return ResponseEntity.created(URI).body(schedule);
    }
}
