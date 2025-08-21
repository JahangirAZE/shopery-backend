package az.shopery.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OwnProductInteractionException extends RuntimeException {
    public OwnProductInteractionException(String message) {
        super(message);
    }
}
