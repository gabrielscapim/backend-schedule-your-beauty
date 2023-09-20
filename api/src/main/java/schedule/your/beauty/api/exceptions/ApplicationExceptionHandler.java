package schedule.your.beauty.api.exceptions;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import schedule.your.beauty.api.dto.DefaultErrorDTO;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleException(Exception exception) {

    DefaultErrorDTO defaultError = new DefaultErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno");

    return new ResponseEntity(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotAvailableDateTimeException.class)
  public ResponseEntity handleExceptionNotAvailableDateTime(){
    DefaultErrorDTO defaultError = new DefaultErrorDTO(HttpStatus.BAD_REQUEST.value(), "A data de agendamento deve estar disponível");

    return new ResponseEntity(defaultError, HttpStatus.BAD_REQUEST);
  }
}
