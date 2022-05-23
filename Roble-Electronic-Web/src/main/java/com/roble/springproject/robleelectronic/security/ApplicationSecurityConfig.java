package com.roble.springproject.RobleElectronic.security;

import com.roble.springproject.RobleElectronic.auth.UserDetailsServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImple();
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/roble_elco", "/roble_elco/", "/roble_elco/products/**", "/roble_elco/category/**", "/index", "/css/*", "/js/*", "/images/*").permitAll()
                .antMatchers(HttpMethod.POST, "/roble_elco/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/roble_elco/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/roble_elco/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/roble_elco/admin/*").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .defaultSuccessUrl("/roble_elco")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
//                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

/*    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userLiban = User.builder()
                .username("liban")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        UserDetails userZamin = User.builder()
                .username("zamin")
                .password(passwordEncoder.encode("password123"))
//                .roles(USER.name())
                .authorities(USER.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(
                userLiban,
                userZamin
        );
    }*/
}
