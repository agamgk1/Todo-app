package io.github.agamgk1.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

// Rozszerzenie oraz adnotacja niezbezne do współpracyz Keyclockiem
//@Enabled pozala nam na korzystanie z adnotacji sluzacych do zabezpieczania (w nawiasach)
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true,
        prePostEnabled = true
)
@KeycloakConfiguration
class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    //dodatkowy bean do keytlocka bez którego aplikacja nie wstanie
    @Bean
    KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    //metoda z dodatkowymi konfiguracjami m.in roli itp
    // Autowired po to zeby spring uwzglednił ta metodę i wstrzyknął auth
    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) {
        //SimpleAuthorityMapper Służy do zmiany/edycji, mapowania rol. Role (nazwy) w springu zaczynają się od "ROLE_"
        var authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setPrefix("ROLE_");
        authorityMapper.setConvertToUpperCase(true);
        KeycloakAuthenticationProvider keycloakProvider = keycloakAuthenticationProvider();
        keycloakProvider.setGrantedAuthoritiesMapper(authorityMapper);
        auth.authenticationProvider(keycloakProvider);
    }

    //Metoda służąca do obsługi rejestracji urzytkownika
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    //metoda służaca do zabezpieczenia określonych informacji - w tym wypadku będzie to /info/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        //do /info/* może wejść tylko użytkownik z rolą USER (pojawi się ekran logowania) - musi być z dużych liter
        http.authorizeRequests()
                .antMatchers("/info/*")
                .hasRole("USER")
                //    .antMatchers("/projects")
                //     .hasRole("ADMIN")
                .anyRequest()
                .permitAll();
    }

}

