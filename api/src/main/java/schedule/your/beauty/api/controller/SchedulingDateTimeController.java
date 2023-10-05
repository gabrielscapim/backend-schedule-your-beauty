package schedule.your.beauty.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schedule.your.beauty.api.dto.DataAddSchedulingDateTimeDTO;
import schedule.your.beauty.api.service.SchedulingDateTimeService;

@RestController
@RequestMapping("scheduling-date-time")
public class SchedulingDateTimeController {

    @Autowired
    private SchedulingDateTimeService schedulingDateTimeService;


    @GetMapping("/time/{day}")
    public ResponseEntity getSchedulingTimesByDay(@PathVariable String day) {
        return ResponseEntity.ok(schedulingDateTimeService.getSchedulingTimesByDay(day));
    }

    @GetMapping("/time/{day}/{production}")
    public ResponseEntity getAvailableSchedulingTimesByDay(@PathVariable String day, @PathVariable String production) {
        return ResponseEntity.ok(schedulingDateTimeService.getAvailableSchedulingTimesByDay(day, production));
    }

    @GetMapping("/date")
    public ResponseEntity getSchedulingDates() {
        return ResponseEntity.ok(schedulingDateTimeService.getSchedulingDates());
    }

    @PostMapping("/time/{date}")
    public ResponseEntity addSchedulingDateTimesByDate(
      @RequestBody @Valid DataAddSchedulingDateTimeDTO dataAddSchedulingDateTimeDTO,
      @PathVariable String date) {
        schedulingDateTimeService.addSchedulingDateTimesByDate(dataAddSchedulingDateTimeDTO, date);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
