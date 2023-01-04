package pt.ipleiria.estg.dei.ei.dae.project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenIssuer {

    protected static final byte[] SECRET_KEY = "secret".getBytes();
    protected static final String ALGORITHM = "DES";

    public static final long EXPIRY_MINS = 60L;

    public String issue(String username) {
        var expiryPeriod = LocalDateTime.now().plusMinutes(EXPIRY_MINS);

        var expirationDateTime = Date.from(
                expiryPeriod.atZone(ZoneId.systemDefault()).toInstant()
        );

        Key key = new SecretKeySpec(SECRET_KEY, ALGORITHM);

        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuedAt(new Date())
                .setExpiration(expirationDateTime)
                .compact();
    }
}
