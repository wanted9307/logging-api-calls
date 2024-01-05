package uz.cyber.proj.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link uz.cyber.proj.entity.User}
 */
@Data
public class UserDto implements Serializable {
    Long id;
    String name;
    String username;
    String email;
}