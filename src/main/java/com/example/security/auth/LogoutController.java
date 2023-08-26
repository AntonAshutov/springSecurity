package com.example.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final AuthenticationService service;

    @GetMapping
    public ResponseEntity<String> logout(){
        service.logout();
        return ResponseEntity.ok("logged out");
    }

}
