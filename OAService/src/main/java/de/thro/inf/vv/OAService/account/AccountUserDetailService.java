package de.thro.inf.vv.OAService.account;
/**
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AccountUserDetailService implements UserDetailsService {

    private final AccountRepo accountRepository;


    @Autowired
    public AccountUserDetailService(AccountRepo accountRepository) {

        this.accountRepository = accountRepository;

    }


    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(username).orElseThrow(() ->

                new UsernameNotFoundException("Could not find user " + username));


        UserDetails userDetails = User.builder().username(username)

                .password(account.getPassword())

                .roles("ADMIN", "USER")

                .build();

        return userDetails;

    }

}*/