package uz.cyber.proj.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogReqResDto implements Serializable {
    String id;
    String uri;
    String httpMethod;
    String ip;
    String request;
    String response;
    String platform;
    Date createdDate;
    UserDto user;
}