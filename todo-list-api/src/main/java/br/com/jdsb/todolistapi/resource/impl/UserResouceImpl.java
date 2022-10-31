package br.com.jdsb.todolistapi.resource.impl;

import br.com.jdsb.todolistapi.model.dto.CredenciaisDTO;
import br.com.jdsb.todolistapi.model.dto.TokenDTO;
import br.com.jdsb.todolistapi.model.entity.User;
import br.com.jdsb.todolistapi.resource.UserResource;
import br.com.jdsb.todolistapi.service.exception.InvalidPasswordException;
import br.com.jdsb.todolistapi.service.impl.JwtServiceImpl;
import br.com.jdsb.todolistapi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserResouceImpl implements UserResource {

    @Autowired
    private  UserServiceImpl userService;
    @Autowired
    private JwtServiceImpl jwtService;

    @Override
    public TokenDTO authenticate(CredenciaisDTO credenciaisDTO) {
        try {
            User user =  User.builder()
                    .userName(credenciaisDTO.getUsername())
                    .password(credenciaisDTO.getPassword())
                    .build();
            userService.validatePassoword(user);
            String token = jwtService.getToken(user);
            return new TokenDTO(user.getUserName(),token);
        }catch (UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }catch (InvalidPasswordException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
