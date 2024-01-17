package fr.lucasdechaumet.pokedexpriceserver.security.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//OncePerRequest because i want to use that class for every request

//Required construct a constuctor with all fields who are final or @NonNull
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException 
	{
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		// because the header must have the jwt we check if its null or not starting with "Bearer"
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			//if contains a valid jwt we'll start to pass the jwt in filters
			filterChain.doFilter(request, response);
			return;
		}
		//extract the jwt from the header
		// 7 because it’s like this
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);
	}

}
