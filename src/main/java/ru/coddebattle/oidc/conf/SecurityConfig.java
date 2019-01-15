package ru.coddebattle.oidc.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.coddebattle.oidc.conf.auth.OAuth2MappingUserService;
import ru.coddebattle.oidc.conf.auth.OidcMappingUserService;
import ru.coddebattle.oidc.conf.auth.UserManager;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserDetailsService mongoUserDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.requestMatchers()
            .antMatchers("/login", "/login/oauth2", "/oauth/authorize", "/oauth2/authorization/**", "/login/oauth2/code/**", "/css/**", "/js/**", "/img/**")
            .and()
            .authorizeRequests()
                .antMatchers("/login", "/css/**", "/js/**", "/img/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
            .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login/oauth2")
                .permitAll()
            .and()
            .oauth2Login()
                .loginPage("/login/oauth2")
                .permitAll()
                .userInfoEndpoint()
                    .userService(new OAuth2MappingUserService(userManager))
                .oidcUserService(new OidcMappingUserService(userManager))
        ;
    } // @formatter:on

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.userDetailsService(mongoUserDetailsService);
    } // @formatter:on

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
