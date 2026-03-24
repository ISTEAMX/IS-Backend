package com.isteamx.university.unitTests;

import com.isteamx.university.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @Mock
    UserDetails userDetails;

    @InjectMocks
    private JwtUtil jwtUtil;

    private final String MOCK_USERNAME = "student@isteamx.com";

    @BeforeEach
    void setUp() {
        String token = "YWNlYXN0YS1lc3RlLW8tY2hlaWUtc2VjcmV0YS1mb2FydGUtbHVuZ2EtcGVudHJ1LXRlc3Rl";
        ReflectionTestUtils.setField(jwtUtil, "secretKey", token);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", 1000 * 60 * 60);
    }

@Test
    void shouldGenerateToken(){
    when(userDetails.getUsername()).thenReturn(MOCK_USERNAME);

    String token = jwtUtil.generateToken(userDetails);

    assertNotNull(token);
    assertEquals(3,token.split("\\.").length);
}
@Test
    void shouldExtractUsername(){
        when(userDetails.getUsername()).thenReturn(MOCK_USERNAME);
        String token = jwtUtil.generateToken(userDetails);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(MOCK_USERNAME,extractedUsername);
    }

    @Test
    void shouldGenerateTokenWithClaims(){
        when(userDetails.getUsername()).thenReturn(MOCK_USERNAME);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Role", "student");

        String token = jwtUtil.generateTokenWithClaims(extraClaims,userDetails);
        String extractedRole = jwtUtil.extractClaim(token, claims -> claims.get("Role",String.class) );

        assertNotNull(token);
        assertEquals("student",extractedRole);
    }

    @Test
    void shouldGenerateTokenCorrectly(){
        when(userDetails.getUsername()).thenReturn(MOCK_USERNAME);
        String token = jwtUtil.generateToken(userDetails);

        boolean isValid = jwtUtil.isTokenValid(token,userDetails);

        assertTrue(isValid);
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid(){
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", -1000);
        when(userDetails.getUsername()).thenReturn(MOCK_USERNAME);
        String expiredToken = jwtUtil.generateToken(userDetails);

        assertThrows(ExpiredJwtException.class,() -> {jwtUtil.isTokenValid(expiredToken,userDetails);});
    }

}
