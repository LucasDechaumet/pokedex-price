package fr.lucasdechaumet.pokedexpriceserver.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	  @Value("${application.security.jwt.secret-key}")
	  private String secretKey;
	  @Value("${application.security.jwt.expiration}")
	  private long jwtExpiration;
	  @Value("${application.security.jwt.refresh-token.expiration}")
	  private long refreshExpiration;
	  @Value("${application.security.jwt.validation-token.expiration}")
	  private long validationExpiration;

	public String extractUsername(String token) {
		// first we send the token and the correct method for extract the username (subject)
		// this method is a method that comes from the interface Claims
		// Claims::getSubject is the same that lambda expression :  claims -> claims.getSubject();
		// the variable claims comes from the function in extractClaim : Function<Claims, T> claimResolver
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		// then our token pass in the extractAllClaims method to return all the claim in Claim object to finally
		// return just the username because the function pass in parameter previously in extractClaim(token, Claims::getSubject);
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				//now we have create a parser who will extract the claims
				.parseClaimsJws(token) // return claims and signature
				.getBody(); //return just the claims
	
	}
	
	public String generateToken(UserDetails userDetails) {
		// the new HashMap empty is use to surcharge the following generateToken if we dont have extra claim
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}
	
	public String generateValidationToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, validationExpiration);
	}
	
	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}
	
	public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey())
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
