package com.happy.transapi.exceptions;

import com.happy.transapi.reponses.GenericResponse;
import com.happy.transapi.reponses.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class DigitalApiExceptionHandler {
    Logger logger = LoggerFactory.getLogger(DigitalApiExceptionHandler.class);

    public static String stackTraceToString(StackTraceElement[] stackTrace) {
            StringWriter sw = new StringWriter();
            printStackTrace(stackTrace, new PrintWriter(sw));
            return sw.toString();
    }

    public static void printStackTrace(StackTraceElement[] stackTrace, PrintWriter pw) {
        for(StackTraceElement stackTraceEl : stackTrace) {
            pw.println(stackTraceEl);
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex) {
        logger.error("EXCEPTION : "+ex.getMessage());


        return new ResponseEntity(new GenericResponse<>(500, "Oops Something Went Wrong"), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity InvalidRequestException(InvalidRequestException exception) {
        BindingResult bindingResult=exception.getBindingResult();

        return new ResponseEntity(new GenericResponse(400, new ValidationResponse().getErrorResponse(bindingResult)), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity AccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity(new GenericResponse(401, "Unauthorized"), null, HttpStatus.UNAUTHORIZED);
    }
}
