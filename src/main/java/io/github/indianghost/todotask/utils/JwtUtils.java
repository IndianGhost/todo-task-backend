package io.github.indianghost.todotask.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unused")
@Component
public class JwtUtils {

    // Secret key for signing JWT (use a strong secret in production)
    private final String secretKey;

    public JwtUtils(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    // Method to generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // Method to extract the username from the JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Method to extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to extract the expiration date from the JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Method to validate a JWT token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Method to create JWT token
    private String createToken(Map<String, Object> claims, String subject) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .claims(claims)  // Custom claims
                .subject(subject)  // Subject (typically username)
                .issuedAt(new Date(System.currentTimeMillis()))  // Token issued time
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Token expiration (10 hours)
                .signWith(key)  // Signing with HS256 algorithm
                .compact();  // Build the token
    }

    // Method to extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        // Generate the SecretKey object from the SECRET_KEY
        SecretKey secretKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());  // Creates an HS256 key

        // Use verifyWith(SecretKey) instead of setSigningKey
        return Jwts.parser()  // Use parserBuilder() instead of parser() in newer versions
                .verifyWith(secretKey)  // Verify JWT with SecretKey
                .build()  // Build the parser
                .parseSignedClaims(token)  // Parse the token and extract claims
                .getPayload();  // Extract the body (claims)
    }

    // Method to check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
