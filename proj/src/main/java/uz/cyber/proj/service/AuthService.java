package uz.cyber.proj.service;

import uz.cyber.proj.dto.LoginDto;
import uz.cyber.proj.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
