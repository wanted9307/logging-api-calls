package uz.cyber.proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cyber.proj.entity.LogReqRes;

public interface LogReqResRepository extends JpaRepository<LogReqRes, String> {
}