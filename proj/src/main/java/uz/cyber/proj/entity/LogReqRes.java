package uz.cyber.proj.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table( name = "LOG_REQ_RES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogReqRes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "ENDPOINT")
    private String uri;
    @Column(name = "HTTP_METHOD")
    private String httpMethod;
    @Column(name = "IP_ADDRESS")
    private String ip;
    @Column(name = "REQUEST")
    private String request;
    @Column(name = "RESPONSE")
    private String response;
    @Column(name = "PLATFORM")
    private String platform;
    @Column(name = "TIMESTAMP_REQ_RES")
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
