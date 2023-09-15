package schedule.your.beauty.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DefaultErrorDTO {
  private int code;
  private String message;
}
