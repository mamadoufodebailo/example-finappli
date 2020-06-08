package fd.app.rs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ApiErrorMessage {
    private String errorMessage;
    private String requestingURI;
    private Instant timeStamp;
}
