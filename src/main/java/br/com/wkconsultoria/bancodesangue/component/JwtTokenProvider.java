package br.com.wkconsultoria.bancodesangue.component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${jwt.token.secret}")
	private String secret;
	
	@Value("${jwt.token.expiration}")
	public long expiration;
	
	public String generateToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public String getEmail(String token) {
		return getClaims(token).getSubject();
	}
	
	public Boolean isTokenExpired(String token) {
		final Date expiration = getClaims(token).getExpiration();
		return expiration.before(new Date());
	}
	
	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}
