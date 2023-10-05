package schedule.your.beauty.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record DataAddSchedulingDateTimeDTO(
  @NotNull(message = "O campo times é obrigatório")
  ArrayList<String> times,
  String lastTimeToSchedule
) {
}
