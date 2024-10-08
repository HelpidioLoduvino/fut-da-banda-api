package com.example.futdabandaapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.futdabandaapi.model.User;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    private final String secret = "356b1fc8-b865-4f81-8d55-c4dc017cbb2e";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("futDaBandaAPI")
                    .withSubject(user.getEmail())
                    //.withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException jce){
            throw new RuntimeException("ERROR WHILE GENERATING TOKEN", jce);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("futDaBandaAPI")
                    .withSubject(user.getEmail())
                    //.withExpiresAt(this.generateRefreshTokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException jce) {
            throw new RuntimeException("ERROR WHILE GENERATING REFRESH TOKEN", jce);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("futDaBandaAPI")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException jce) {
            throw new RuntimeException("ERROR WHILE VALIDATING TOKEN", jce);
        }
    }
    /*

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.of("+01:00"));
    }

    private Instant generateRefreshTokenExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("+01:00"));
    }

     */

}
