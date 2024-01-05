package uz.cyber.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
