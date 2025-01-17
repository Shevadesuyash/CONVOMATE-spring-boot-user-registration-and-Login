package net.enjoy.springboot.registrationlogin.security;

import java.util.Collection;
import java.util.stream.Collectors;
import net.enjoy.springboot.registrationlogin.entity.Role;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Service class implementing Spring Security's UserDetailsService interface
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  // Constructor-based dependency injection
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Method to load user details by username (email)
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // Find user by email in the repository
    User user = userRepository.findByEmail(email);

    // If user is found
    if (user != null) {
      // Create UserDetails object with username (email), password, and authorities (roles)
      return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    } else {
      // Throw exception if user is not found
      throw new UsernameNotFoundException("Invalid username or password.");
    }
  }

  // Method to map user roles to Spring Security GrantedAuthority objects
  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
    // Map each role to a GrantedAuthority object
    Collection<? extends GrantedAuthority> authorities =
        roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    return authorities;
  }
}
