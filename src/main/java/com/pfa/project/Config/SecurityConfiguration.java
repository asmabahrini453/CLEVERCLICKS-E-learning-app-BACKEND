package com.pfa.project.Config;

import com.pfa.project.Config.Jwt.JwtAuthenticationFilter;
import com.pfa.project.Entities.Enum.Permission;
import com.pfa.project.Entities.Enum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers(
                        "/api/pfa/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()

                .requestMatchers("/api/pfa/user/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .requestMatchers(HttpMethod.GET, "/api/pfa/user/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.USER_READ.name())
                .requestMatchers(HttpMethod.POST, "/api/pfa/user/**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.USER_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "/api/pfa/user/**").hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.USER_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/pfa/user/**").hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.USER_DELETE.name())

                .requestMatchers("/api/pfa/course/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .requestMatchers(HttpMethod.GET, "/api/pfa/course/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.USER_READ.name())
                .requestMatchers(HttpMethod.POST, "/api/pfa/course/**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.USER_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "/api/pfa/course/**").hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.USER_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/pfa/course/**").hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.USER_DELETE.name())

                .requestMatchers("/api/pfa/category/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .requestMatchers(HttpMethod.GET, "/api/pfa/category/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.USER_READ.name())
                .requestMatchers("/api/pfa/category/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/pfa/category/**").hasAuthority(Permission.ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "/api/pfa/category/**").hasAuthority(Permission.ADMIN_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/pfa/category/**").hasAuthority(Permission.ADMIN_DELETE.name())


                .requestMatchers("/api/pfa/admin/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/api/pfa/admin/**").hasAuthority(Permission.ADMIN_READ.name())
                .requestMatchers(HttpMethod.POST, "/api/pfa/admin/**").hasAuthority(Permission.ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "/api/pfa/admin/**").hasAuthority(Permission.ADMIN_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/pfa/admin/**").hasAuthority(Permission.ADMIN_DELETE.name())



                .anyRequest().authenticated()
                .and()
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //STATELESS we don't want to store the session
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/pfa/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // Adjust as needed
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
