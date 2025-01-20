package academy.devdojo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailExistsException extends ResponseStatusException {
    public EmailExistsException(String email) {
        super(HttpStatus.BAD_REQUEST, "E-mail %s already exists".formatted(email));
    }
}
