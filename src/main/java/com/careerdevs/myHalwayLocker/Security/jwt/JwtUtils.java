package com.careerdevs.myHalwayLocker.Security.jwt;

import com.careerdevs.myHalwayLocker.Security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${hallway.app.jwtSecret}")
    public String jwtSecret;

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException e){
            logger.error("Invalid JWT token : {}", e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("JWT Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("JWT is unsupported : {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT Claims string is empty : {}", e.getMessage());
        }
        return false;
    }

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrinciple = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
