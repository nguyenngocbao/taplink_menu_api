package taplink.network.menu.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import taplink.network.menu.api.dto.ErrorDetails;
import taplink.network.menu.api.exception.ResourceNotFoundException;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionController {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        logger.error("ResourceNotFoundException", ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
