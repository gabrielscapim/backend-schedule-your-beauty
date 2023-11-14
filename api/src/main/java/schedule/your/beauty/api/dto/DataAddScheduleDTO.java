package schedule.your.beauty.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record DataAddScheduleDTO(
        @NotBlank(message = "O campo clientName é obrigatório")
        String clientName,

        @Size(min = 11,message = "O campo clientNumber deve possuir 11 caracteres")
        @Size(max = 11,message = "O campo clientNumber deve possuir 11 caracteres")
        @NotBlank(message = "O campo clientNumber é obrigatório")
        String clientNumber,

        @Pattern(
          regexp = "^(Penteado|Maquiagem|Maquiagem e Penteado)$",
          message = "O campo productionName deve ser 'Penteado' ou 'Maquiagem' ou 'Maquiagem e Penteado'"
        )
        @NotBlank(message = "O campo productionName é obrigatório")
        String productionName,
        @NotBlank(message = "O campo eventName é obrigatório")
        String eventName,
        @NotBlank(message = "O campo event schedulingDateTime é obrigatório")
        String schedulingDateTime
) {
        public DataAddScheduleDTO {

        }
}
