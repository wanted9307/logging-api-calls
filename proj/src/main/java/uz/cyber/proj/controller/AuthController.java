package uz.cyber.proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cyber.proj.dto.JwtAuthResponse;
import uz.cyber.proj.dto.LoginDto;
import uz.cyber.proj.dto.RegisterDto;
import uz.cyber.proj.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
