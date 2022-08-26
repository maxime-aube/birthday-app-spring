package fr.maximob.birthdayapp.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.maximob.birthdayapp.spring.filter.CustomAuthenticationFilter;
import fr.maximob.birthdayapp.spring.filter.CustomAuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = { "/login" };
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super();
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().httpBasic().and().authorizeRequests().antMatchers(PUBLIC_MATCHERS)
                .permitAll().anyRequest().authenticated().and().formLogin().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//		http
//        .httpBasic()
//        .and()
//        .authorizeRequests()
//        .antMatchers(HttpMethod.POST, "/users").permitAll()
//        .antMatchers(HttpMethod.PUT, "/users").authenticated()
//        .antMatchers(HttpMethod.DELETE, "/users").authenticated()
//        .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomAuthorizationFilter customAuthorizationFilter() {
        return new CustomAuthorizationFilter();
    }
}
