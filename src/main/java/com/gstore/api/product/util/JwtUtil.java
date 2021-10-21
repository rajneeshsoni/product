package com.gstore.api.product.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	public String getJwt(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader(ApiConstants.JWT_HEADER_KEY);
		String jwt = null;
		if (null != authorizationHeader && authorizationHeader.startsWith(ApiConstants.JWT_HEADER_VALUE_PREFIX)) {
			jwt = authorizationHeader.substring(7);
		}
		return jwt;
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(ApiConstants.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * ApiConstants.JWT_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, ApiConstants.JWT_SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String getUsername(HttpServletRequest request) {
		return extractUsername(getJwt(request));
	}

	public String getUsername(String jwt) {
		String username = null;
		if (null != jwt && !jwt.isEmpty()) {
			username = extractUsername(jwt);
		}
		return username;
	}

}
