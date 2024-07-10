package pro.sky.Stream_Option_2_8.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongNameException extends RuntimeException {
    public WrongNameException() {
    }

    public WrongNameException(String message) {
        super(message);
    }
}