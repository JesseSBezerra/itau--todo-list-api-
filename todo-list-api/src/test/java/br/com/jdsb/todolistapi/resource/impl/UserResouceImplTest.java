package br.com.jdsb.todolistapi.resource.impl;

import br.com.jdsb.todolistapi.model.dto.CredenciaisDTO;
import br.com.jdsb.todolistapi.model.dto.TokenDTO;
import br.com.jdsb.todolistapi.service.impl.JwtServiceImpl;
import br.com.jdsb.todolistapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResouceImplTest {

    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    @InjectMocks
    private UserResouceImpl userResouce;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private JwtServiceImpl jwtService;

    private CredenciaisDTO credenciaisDTO;

    private TokenDTO tokenDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpData();
    }

    @Test
    void authenticate() {
        userResouce.authenticate(credenciaisDTO);
    }

    @Test
    void authenticateWhenError() {
        when(jwtService.getToken(any())).thenThrow(new UsernameNotFoundException(UNAUTHORIZED));
        try {
            userResouce.authenticate(credenciaisDTO);
        }catch (Exception ex){
            assertEquals(ResponseStatusException.class, ex.getClass());
        }
    }
    public void setUpData(){
        credenciaisDTO = new CredenciaisDTO("username","password");
        tokenDTO = new TokenDTO("username","token");
    }
}