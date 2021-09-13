import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;
import java.util.NoSuchElementException;

@EnableWebMvc
@ControllerAdvice(basePackages = {"com.example.delivery_system.controller"} )
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ NoSuchElementException.class})
    protected ResponseEntity<Object> handleMissingElement(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "There is no item with the specified id";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { ParseException.class})
    protected ResponseEntity<Object> handleMappingError(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Error during class mapping";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.resolve(422), request);
    }
}
