package net.enjoy.springboot.registrationlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// Configuration class for Spring Security
@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    // Bean for password encoder
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security filter chain configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests(authorize -> authorize
                // Permit access to registration page
                .requestMatchers("/register/**").permitAll()
                // Permit access to index page
                .requestMatchers("/index").permitAll()
                // Allow access to users page only for ADMIN role
                .requestMatchers("/users").hasRole("ADMIN")
                // Require authentication for model page
                .requestMatchers("/model").authenticated()
            )
            .formLogin(form -> form
                // Customize login page and authentication URL
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // Redirect to model page after successful login
                .defaultSuccessUrl("/model", true)
                .permitAll()
            )
            .logout(logout -> logout
                // Customize logout URL
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
            );
        return http.build();
    }

    // Global authentication configuration
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
