package schedule.your.beauty.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DataAddScheduleDTO(
        @NotBlank(message = "O campo clientName é obrigatório")
        String clientName,
        @NotBlank(message = "O campo clientNumber é obrigatório")
        String clientNumber,
        @NotBlank(message = "O campo productionName é obrigatório")
        String productionName,
        @NotBlank(message = "O campo eventName é obrigatório")
        String eventName,
        @NotBlank(message = "O campo event schedulingDateTime é obrigatório")
        String schedulingDateTime
) {
}
