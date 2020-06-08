package fd.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class NotFoundException extends HttpStatusCodeException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
