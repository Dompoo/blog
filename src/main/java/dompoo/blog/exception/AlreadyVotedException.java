package dompoo.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "already voted")
public class AlreadyVotedException extends RuntimeException {
    private static final long serialVersionUID = 2L;
    public AlreadyVotedException(String message) {
        super(message);
    }
}