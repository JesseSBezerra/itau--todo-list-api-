package br.com.jdsb.todolistapi.resource.exceptions;


import br.com.jdsb.todolistapi.service.exception.InvalidPasswordException;
import br.com.jdsb.todolistapi.service.exception.TaskNotFoundException;
import br.com.jdsb.todolistapi.service.exception.UserNotInformedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<StandardError> taskNotFound(TaskNotFoundException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new StandardError(LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI())
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<StandardError> invalidPassword(InvalidPasswordException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new StandardError(LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI())
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> unauthorized(ResponseStatusException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new StandardError(LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value(),
                        request.getRequestURI())
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<StandardError> expiredJwtException(ExpiredJwtException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new StandardError(LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value(),
                        request.getRequestURI())
        );
    }

    @ExceptionHandler(UserNotInformedException.class)
    public ResponseEntity<StandardError> userNotInformed(UserNotInformedException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new StandardError(LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value(),
                        request.getRequestURI())
        );
    }



}
