package com.example.security.auth;

import com.example.security.config.jwt.TokenGenerator;
import com.example.security.role.Role;
import com.example.security.role.RoleRepo;
import com.example.security.user.User;
import com.example.security.user.UserRepo;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    public String register(RegisterRequest request) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepo.findById(2).get());
        roles.add(roleRepo.findById(3).get());
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userRepo.save(user);
        return "registration complete";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws IllegalArgumentException{
        var user = userRepo.findByEmail(request.getEmail())
                .orElseThrow();
        Assert.isTrue(passwordEncoder.matches(request.getPassword(), user.getPassword()), "wrong pass");
        var jwtToken = tokenGenerator.createToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
