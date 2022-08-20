package com.devsuperior.bds04.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String[] PUBLIC = { "/oauth/token" };
    private static final String[] GET_PUBLIC = { "/cities/**", "/events/**" };
    private static final String[] CLIENT_OR_ADMIN = { "/events/**" };

    private final JwtTokenStore jwtTokenStore;

    public ResourceServerConfig(JwtTokenStore jwtTokenStore) {
        this.jwtTokenStore = jwtTokenStore;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
        .antMatchers(HttpMethod.GET, GET_PUBLIC).permitAll()
        .antMatchers(CLIENT_OR_ADMIN).hasAnyRole("CLIENT", "ADMIN")
		.anyRequest().hasAnyRole("ADMIN");
    }

}
