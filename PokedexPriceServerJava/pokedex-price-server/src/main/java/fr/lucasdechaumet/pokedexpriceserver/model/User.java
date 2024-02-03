package fr.lucasdechaumet.pokedexpriceserver.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.lucasdechaumet.pokedexpriceserver.security.token.Token;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity implements UserDetails {

	private String firstname;
	
	private String lastname;
	
	@Column(unique = true)
	private String nickname;
	
	private LocalDate birthDate;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private boolean isActivated;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @OneToOne
    private Trainer trainer;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
//	@Override
//	public String toString() {
//	    return "User{" +
//	            "firstname='" + firstname + '\'' +
//	            ", lastname='" + lastname + '\'' +
//	            ", nickname='" + nickname + '\'' +
//	            ", birthDate=" + birthDate +
//	            ", email='" + email + '\'' +
//	            ", password='" + password + '\'' +
//	            ", isActivated=" + isActivated +
//	            ", role=" + role +
//	            '}';
//	}

}
