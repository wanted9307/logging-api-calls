package uz.cyber.proj.service.impl;


import org.springframework.stereotype.Service;
import uz.cyber.proj.dto.LogReqResDto;
import uz.cyber.proj.dto.UserDto;
import uz.cyber.proj.entity.LogReqRes;
import uz.cyber.proj.entity.User;
import uz.cyber.proj.repository.LogReqResRepository;
import uz.cyber.proj.service.LogReqResService;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class LogReqResServiceImpl implements LogReqResService {
    private final LogReqResRepository logReqResRepository;


    public LogReqResServiceImpl(LogReqResRepository logReqResRepository) {
        this.logReqResRepository = logReqResRepository;

    }

    @Override
    public List<LogReqResDto> getAll() {
//        System.out.println(logReqResRepository.findAll().stream().map(logReqResRepository->entityToDto(logReqResRepository)).collect(Collectors.toList()));
        return logReqResRepository.findAll().stream().map(logReqResRepository->entityToDto(logReqResRepository)).collect(Collectors.toList());
    }
    private LogReqResDto entityToDto(LogReqRes logReqRes){
        LogReqResDto logReqResDto = new LogReqResDto();
        logReqResDto.setId(logReqRes.getId());
        logReqResDto.setIp(logReqRes.getIp());
        logReqResDto.setRequest(logReqRes.getRequest());
        logReqResDto.setUri(logReqRes.getUri());
        logReqResDto.setPlatform(logReqRes.getPlatform());
        logReqResDto.setUser(userToDto(logReqRes.getUser()));
        logReqResDto.setResponse(logReqRes.getResponse());
        logReqResDto.setCreatedDate(logReqRes.getCreatedDate());
        logReqResDto.setHttpMethod(logReqResDto.getHttpMethod());
        return logReqResDto;
    }
    private UserDto userToDto(User user){
        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
