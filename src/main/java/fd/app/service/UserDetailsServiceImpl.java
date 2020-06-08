package fd.app.service;

import fd.app.business.AccountService;
import fd.app.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUserOptional = accountService.findUserByUsername(username);

        if(!appUserOptional.isPresent())
            throw new UsernameNotFoundException(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUserOptional.get().getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });

        AppUser user = appUserOptional.get();

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
