package com.ftn.micro1.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

	@Value("thesecretkeyforthetoken6526")
	private String SIGNING_KEY;
	
	@Value("18000")
	private Long expiration;

	public String generateToken(Authentication auth) {
		
		UserSecurity userDetails = (UserSecurity) auth.getPrincipal();
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", userDetails.getUsername());
		claims.put("authorities", userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList()));
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getId().toString())
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
	}
	
	
	
	public long getExpiredIn() {
		return expiration;
	}
	
	public UserSecurity getUserSecurity(String jwt) throws Exception {
		try {
			Claims claims = getClaimsFromToken(jwt);
			
			return new UserSecurity(
				Long.parseLong(getIdFromClaims(claims)), 
				getUsernameFromClaims(claims), 
				true,
				getGrantedAuthoritiesFromClaims(claims),true
			);
		} catch (Exception e) {
			throw new Exception(e);
		}		
	}
	
	private Claims getClaimsFromToken(String authToken) throws Exception {
		return Jwts.parser()
			.setSigningKey(SIGNING_KEY)
			.parseClaimsJws(authToken)
			.getBody();
	}
	
	private String getIdFromClaims(Claims claims) {
		return claims.getSubject();		
	}
	
	private String getUsernameFromClaims(Claims claims) {
		return (String) claims.get("username");
	}
	
	private List<GrantedAuthority> getGrantedAuthoritiesFromClaims(Claims claims) {
		List<String> authorities = (List<String>) claims.get("authorities");			
		return authorities.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}
	

}
