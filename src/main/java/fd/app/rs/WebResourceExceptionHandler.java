package fd.app.rs;

import fd.app.exception.NotFoundException;
import fd.app.rs.ApiErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class WebResourceExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        Instant instant = Instant.now();
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(ex.getMessage(), request.getDescription(false), instant);
        return new ResponseEntity<>(apiErrorMessage, ex.getStatusCode());
    }

    @ExceptionHandler({ Exception.class })
    public final ResponseEntity<Object> handleVehicleGeneralException(Exception ex, WebRequest request) {
        Instant instant = Instant.now();
        ApiErrorMessage ApiErrorMessage = new ApiErrorMessage(ex.getLocalizedMessage(), request.getDescription(true), instant);
        return new ResponseEntity<>(ApiErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
