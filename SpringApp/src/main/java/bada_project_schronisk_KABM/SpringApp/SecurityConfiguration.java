package bada_project_schronisk_KABM.SpringApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("user")
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/", "/index").permitAll() // Allow access to index page
                .requestMatchers("/resources/**", "/static/**", "/webjars/**").permitAll() // Allow static resources
                .requestMatchers("/main").authenticated() // Require authentication for /main
                .requestMatchers("/main_admin").hasRole("ADMIN") // Restrict /main_admin to ADMIN role
                .requestMatchers("/main_user").hasRole("USER") // Restrict /main_user to USER role
                .anyRequest().authenticated() // All other requests require authentication
                .and()
                .formLogin()
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/main", true) // Redirect to /main after successful login
                .permitAll() // Allow everyone to access the login page
                .and()
                .logout()
                .logoutUrl("/index") // Specify logout URL
                .logoutSuccessUrl("/index") // Redirect to /index after logout
                .permitAll(); // Allow everyone to access logout functionality



        return http.build();
    }
}
