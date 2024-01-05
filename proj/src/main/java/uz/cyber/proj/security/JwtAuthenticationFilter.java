package uz.cyber.proj.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.cyber.proj.repository.LogReqResRepository;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get JWT token from http request
        String token = getTokenFromRequest(request);

        // validate token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // get username from token
            String username = jwtTokenProvider.getUsername(token);

            // load the user associated with token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response);

    }
    private String getTokenFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }
//    private String getValueAsString(byte[] contentAsByteArray, String characterEncoding){
//        String dataAsString="";
//        try {
//            dataAsString = new String(contentAsByteArray,characterEncoding);
//        }catch (Exception e){
//            log.error("Exception occured while converting byte into an array{}",e.getMessage());
//            e.printStackTrace();
//        }
//        return dataAsString;
//    }
//    @Async
//    protected void logReqRes(String requestBody, String responseBody, String uri, String method){
//        LogReqRes logReqRes = new LogReqRes();
//        logReqRes.setRequest(requestBody);
//        logReqRes.setResponse(responseBody);
//        logReqRes.setUri(uri);
//        logReqRes.setHttpMethod(method);
//        logReqRes.setCreatedDate(new Date());
//        logReqResRepository.save(logReqRes);
//    }



}
