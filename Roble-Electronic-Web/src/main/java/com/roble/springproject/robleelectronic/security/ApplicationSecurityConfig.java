package com.roble.springproject.robleelectronic.security;

/*import com.roble.springproject.robleelectronic.auth.UserDetailsServiceImple;*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.roble.springproject.robleelectronic.security.UserPermissions.CATEGORY_WRITE;
import static com.roble.springproject.robleelectronic.security.UserRoles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    public final PasswordEncoder passwordEncoder;
    /*private final UserDetailsServiceImple userDetailsServiceImple;*/

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/roble_elco/", "/index", "/css/*", "/js/*", "/images/*").permitAll()
                .antMatchers(HttpMethod.POST, "/roble_elco/admin/*").hasAuthority(CATEGORY_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/roble_elco/admin/*").hasAuthority(CATEGORY_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/roble_elco/admin/*").hasAuthority(CATEGORY_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/roble_elco/admin/*").hasAuthority(CATEGORY_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
                /*.loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/roble_elco")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login")*/
//                .httpBasic();
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsServiceImple);
        return provider;
    }*/

    @Override
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
    }
}
