package br.com.jdsb.todolistapi.service.impl;

import br.com.jdsb.todolistapi.model.entity.User;
import br.com.jdsb.todolistapi.repository.UserRepository;
import br.com.jdsb.todolistapi.service.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    public void validatePassoword(User userLogin){
        UserDetails user = loadUserByUsername(userLogin.getUserName());
        boolean isValid = encoder.matches(userLogin.getPassword(),user.getPassword());
        if(!isValid){
            throw new InvalidPasswordException("Username or Password is not valid");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username).orElseThrow(()-> new InvalidPasswordException("Username or Password is not valid"));
        String[] roles = user.getIsSuper() ? new String[]{"SUPER","USER"} : new String[]{"USER"};
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    public User getSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUserName(userDetails.getUsername()).get();
    }
}
