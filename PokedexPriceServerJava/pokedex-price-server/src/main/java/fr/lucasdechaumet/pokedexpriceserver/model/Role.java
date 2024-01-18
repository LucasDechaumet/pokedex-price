package fr.lucasdechaumet.pokedexpriceserver.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	NEW_TRAINER,
	VIP_TRAINER,
	ADMIN;

	 public Collection<? extends GrantedAuthority> getAuthorities() {
	        Set<GrantedAuthority> authorities = new HashSet<>();
	        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
	        return authorities;
	    }
}
