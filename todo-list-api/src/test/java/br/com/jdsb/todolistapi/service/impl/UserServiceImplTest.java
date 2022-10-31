package br.com.jdsb.todolistapi.service.impl;

import br.com.jdsb.todolistapi.model.entity.User;
import br.com.jdsb.todolistapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;

    public static final long ID = 1L;
    public static final String USER_NAME = "test";
    public static final String PASSWORD = "teste";

    private User user;
    private Optional<User> userOptional;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpData();
    }

    @Test
    void validatePassoword() {
        when(repository.findByUserName(anyString())).thenReturn(userOptional);
        when(encoder.matches(any(), any())).thenReturn(true);
        userService.validatePassoword(user);
        verify(encoder, times(1));
    }

    @Test
    void loadUserByUsername() {
        when(repository.findByUserName(anyString())).thenReturn(userOptional);
        UserDetails user = userService.loadUserByUsername(anyString());
        assertNotNull(user);
    }

    @Test
    void getSessionUser() {
    }

    private void setUpData(){
        user = new User(ID, USER_NAME, PASSWORD, false);
        userOptional = Optional.of(user);
        userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(new String[]{"SUPER","USER"})
                .build();
    }
}