package br.com.jdsb.todolistapi.service.impl;

import br.com.jdsb.todolistapi.model.entity.User;
import br.com.jdsb.todolistapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.timeExpire}")
    private Long time;

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Override
    public String getToken(User user) {
        LocalDateTime timeExpire = LocalDateTime.now().plusMinutes(time);
        Instant instant = timeExpire.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    @Override
    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getLoguedUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }

}
