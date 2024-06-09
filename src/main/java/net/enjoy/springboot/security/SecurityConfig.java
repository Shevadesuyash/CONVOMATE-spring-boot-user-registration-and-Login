package net.enjoy.springboot.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    @Autowired
    private DataSource dataSource;

    // Configure HTTP security
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // Restrict access to /users.html to users with ADMIN role
            .requestMatchers("/users.html").hasRole("ADMIN")
            // Allow access to any other request for authenticated users
            .anyRequest().authenticated()
            .and()
            // Configure form login
            .formLogin()
            .and()
            // Configure logout
            .logout()
            .logoutUrl("/logout") // Logout URL
            .logoutSuccessUrl("/") // Redirect to home page after logout
            .invalidateHttpSession(true) // Invalidate session
            .deleteCookies("JSESSIONID") // Delete cookies
            .and()
            .csrf().disable(); // Disable CSRF protection
    }

    // Configure authentication manager
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource) // Set data source for authentication
            .usersByUsernameQuery("SELECT email, password, 1 as enabled FROM users WHERE email=?") // Query to fetch user details by email
            .authoritiesByUsernameQuery("SELECT u.email, r.name FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON ur.role_id = r.id WHERE u.email=?") // Query to fetch user authorities by email
            .passwordEncoder(passwordEncoder()); // Set password encoder
    }

    // Bean for password encoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
