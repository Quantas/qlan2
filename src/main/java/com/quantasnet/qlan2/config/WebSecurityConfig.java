package com.quantasnet.qlan2.config;

import com.quantasnet.qlan2.security.QlanUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "qlan2-remember-me-68de78bb-a234-csw3-8508-ab9f8dasdf2a";

    @Autowired
    private Environment env;

    @Autowired
    private PersistentTokenRepository qlanPersistentTokenRepository;

    @Autowired
    private AuthenticationUserDetailsService<OpenIDAuthenticationToken> steamUserDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        String channel = "REQUIRES_INSECURE_CHANNEL";
        if (env.acceptsProfiles("openshift")) {
            channel = "REQUIRES_SECURE_CHANNEL";
        }

        http
            .authorizeRequests()
                .antMatchers("/profile/**").authenticated()
                .anyRequest().permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
                .openidLogin()
                .loginPage("/login")
                .permitAll()
                .authenticationUserDetailsService(steamUserDetailsService)
            .and()
                .logout()
                .permitAll()
            .and()
                .requiresChannel().anyRequest().requires(channel)
            .and()
                .sessionManagement().sessionFixation().changeSessionId()
            .and()
                .rememberMe().key(KEY).rememberMeServices(rememberMeServices());
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(qlanUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService qlanUserDetailsService() {
        return new QlanUserDetailsService();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        final PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices(KEY, qlanUserDetailsService(), qlanPersistentTokenRepository);
        rememberMeServices.setCookieName("QLAN2_REMEMBER_ME");
        rememberMeServices.setParameter("_qlan2_remember_me");
        return rememberMeServices;
    }

    @Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
        return new RememberMeAuthenticationFilter(authenticationManagerBean(), rememberMeServices());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
}
