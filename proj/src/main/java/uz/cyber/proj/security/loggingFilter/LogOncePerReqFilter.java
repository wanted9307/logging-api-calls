package uz.cyber.proj.security.loggingFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import uz.cyber.proj.entity.LogReqRes;
import uz.cyber.proj.repository.LogReqResRepository;
import uz.cyber.proj.repository.UserRepository;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
@Order(-1)
public class LogOncePerReqFilter extends OncePerRequestFilter {

    private final LogReqResRepository logReqResRepository;
    private final UserRepository userRepository;

    public LogOncePerReqFilter(LogReqResRepository logReqResRepository,UserRepository userRepository) {
        this.logReqResRepository = logReqResRepository;
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper =new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cachingRequestWrapper,cachingResponseWrapper);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        String requestBody = getValueAsString(cachingRequestWrapper.getContentAsByteArray(), cachingRequestWrapper.getCharacterEncoding());
        String responseBody = getValueAsString(cachingResponseWrapper.getContentAsByteArray(), cachingResponseWrapper.getCharacterEncoding());
        String ip = cachingRequestWrapper.getRemoteAddr();
        String platform = cachingRequestWrapper.getHeader("sec-ch-ua-platform")==null?cachingRequestWrapper.getHeader("user-agent"):without(cachingRequestWrapper.getHeader("sec-ch-ua-platform"));

        String usernameOrEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logReqRes(requestBody, responseBody, ip,usernameOrEmail,platform,cachingRequestWrapper.getRequestURI(),cachingRequestWrapper.getMethod());

        cachingResponseWrapper.copyBodyToResponse();
    }
    private String getValueAsString(byte[] contentAsByteArray, String characterEncoding){
        String dataAsString="";
        try {
            dataAsString = new String(contentAsByteArray,characterEncoding);
        }catch (Exception e){
            log.error("Exception occured while converting byte into an array{}",e.getMessage());
            e.printStackTrace();
        }
        return dataAsString;
    }
    @Async
    protected void logReqRes(String requestBody, String responseBody, String ip,String usernameOrEmail,String platform,String uri, String method){
        LogReqRes logReqRes = new LogReqRes();
        logReqRes.setRequest(requestBody);
        logReqRes.setResponse(responseBody);
        logReqRes.setUri(uri);
        logReqRes.setIp(ip);
        logReqRes.setUser(userRepository.findUserByEmail(usernameOrEmail));
        logReqRes.setPlatform(platform);
        logReqRes.setHttpMethod(method);
        logReqRes.setCreatedDate(new Date());
        logReqResRepository.save(logReqRes);
    }
    private String without(String string){
        return string.replace("\"","");
    }


}
