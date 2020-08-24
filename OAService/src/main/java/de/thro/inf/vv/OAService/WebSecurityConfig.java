package de.thro.inf.vv.OAService;
/**
import de.thro.inf.vv.OAService.account.AccountRepo;
import de.thro.inf.vv.OAService.account.AccountUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final AccountUserDetailService accountDetailsService;


    @Bean

    @Override

    protected AccountUserDetailService userDetailsService() {

        return accountDetailsService;

    }


    @Autowired

    public WebSecurityConfig(AccountRepo accountRepository) {

        accountDetailsService = new AccountUserDetailService(accountRepository);

    }


    @Override

    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();

        httpSecurity.headers().frameOptions().disable();

        httpSecurity.authorizeRequests()

                .antMatchers("/**").permitAll()

                .anyRequest()

                .fullyAuthenticated()

                .and()

                .httpBasic();

    }


    @Bean

    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(accountDetailsService);

        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;

    }


}
*/