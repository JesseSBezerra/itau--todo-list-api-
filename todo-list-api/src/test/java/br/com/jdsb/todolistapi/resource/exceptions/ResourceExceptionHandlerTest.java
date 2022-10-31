package br.com.jdsb.todolistapi.resource.exceptions;

import br.com.jdsb.todolistapi.service.exception.InvalidPasswordException;
import br.com.jdsb.todolistapi.service.exception.TaskNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    public static final String TASK_NOT_FOUND = "Task Not Found";
    public static final String INVALID_PASSWORD = "Invalid Password";

    public static final String UNAUTHORIZED = "Unauthorized";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void taskNotFound() {
        ResponseEntity<StandardError> response = exceptionHandler
                .taskNotFound(
                        new TaskNotFoundException(TASK_NOT_FOUND),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(TASK_NOT_FOUND, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void invalidPassword() {
        ResponseEntity<StandardError> response = exceptionHandler
                .invalidPassword(
                        new InvalidPasswordException(INVALID_PASSWORD),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(INVALID_PASSWORD, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void unauthorized() {
        ResponseEntity<StandardError> response = exceptionHandler
                .unauthorized(
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(401, response.getBody().getStatus());
    }

    @Test
    void expiredJwtException() {
        ResponseEntity<StandardError> response = exceptionHandler
                .expiredJwtException(
                        new ExpiredJwtException(null,null,UNAUTHORIZED),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(UNAUTHORIZED, response.getBody().getError());
        assertEquals(401, response.getBody().getStatus());
    }
}