package fr.lucasdechaumet.pokedexpriceserver.security.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
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
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	
	private final UserDetailsService userDetailsService;

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
		// we check if there is an email and because of securityContex we can know if the user is connected yet
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			// now i have a full userDetails and the user is not connected i can check if the token is valid
			if (jwtService.isTokenValid(jwt, userDetails)) {
				// this object is needed by SecurityContextHolder to update securityContext
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
