package schedule.your.beauty.api.dto;

import jakarta.validation.constraints.NotBlank;
import schedule.your.beauty.api.model.SchedulingDateTime;

import java.util.Date;
import java.util.List;

public record DataAddScheduleDTO(
        @NotBlank(message = "O campo name é obrigatório")
        String clientName,
        @NotBlank(message = "O campo number é obrigatório")
        String clientNumber,
        @NotBlank(message = "O campo productionName é obrigatório")
        String productionName,
        @NotBlank(message = "O campo eventName é obrigatório")
        String eventName,

        @NotBlank(message = "O campo event schedulingDateTimes é obrigatório")
        String schedulingDateTime
//        @NotBlank(message = "O campo event date é obrigatório")
//        Date eventDate,
//        @NotBlank(message = "O campo event time é obrigatório")
//        String eventTime
) {
}
