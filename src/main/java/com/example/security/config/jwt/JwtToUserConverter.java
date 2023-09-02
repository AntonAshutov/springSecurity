package com.example.security.config.jwt;

import com.example.security.user.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        User user = new User();
        user.setEmail(jwt.getSubject());
//        user.setRoles(jwt.getClaim("roles"));
        return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities()); //todo поменять токен
    }
}
