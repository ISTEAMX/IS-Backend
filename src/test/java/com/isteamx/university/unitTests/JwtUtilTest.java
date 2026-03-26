package com.isteamx.university.unitTests;
import com.isteamx.university.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

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

    String token = jwtUtil.generateToken(MOCK_USERNAME);

    assertNotNull(token);
    assertEquals(3,token.split("\\.").length);
}
@Test
    void shouldExtractUsername(){

        String token = jwtUtil.generateToken(MOCK_USERNAME);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(MOCK_USERNAME,extractedUsername);
    }

    @Test
    void shouldGenerateTokenWithClaims(){

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Role", "student");

        String token = jwtUtil.generateTokenWithClaims(extraClaims,MOCK_USERNAME);
        String extractedRole = jwtUtil.extractClaim(token, claims -> claims.get("Role",String.class) );

        assertNotNull(token);
        assertEquals("student",extractedRole);
    }

    @Test
    void shouldGenerateTokenCorrectly(){

        String token = jwtUtil.generateToken(MOCK_USERNAME);

        boolean isValid = jwtUtil.isTokenValid(token,MOCK_USERNAME);

        assertTrue(isValid);
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid(){


        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", -1000);
        String expiredToken = jwtUtil.generateToken(MOCK_USERNAME);

        assertThrows(ExpiredJwtException.class,() -> jwtUtil.isTokenValid(expiredToken,MOCK_USERNAME));
    }

}
