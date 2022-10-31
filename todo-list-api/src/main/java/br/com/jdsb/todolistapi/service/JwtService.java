package br.com.jdsb.todolistapi.service;

import br.com.jdsb.todolistapi.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {

    public String getToken(User user);

    public Claims getClaims(String token) throws ExpiredJwtException;
}
