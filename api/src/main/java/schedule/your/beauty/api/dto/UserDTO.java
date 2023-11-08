package schedule.your.beauty.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(

        @NotBlank(message = "O campo userName é obrigatório")
        String username,
        @NotBlank(message = "O campo password é obrigatório")
        String password
) {
}
