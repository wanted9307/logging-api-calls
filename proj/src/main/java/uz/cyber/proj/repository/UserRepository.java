package uz.cyber.proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cyber.proj.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    User findUserByEmail(String emailOrUsername);
}