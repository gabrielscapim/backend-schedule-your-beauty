package schedule.your.beauty.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ValidationErrorDTO {
  private int code;
  private List<String> messages;
}
