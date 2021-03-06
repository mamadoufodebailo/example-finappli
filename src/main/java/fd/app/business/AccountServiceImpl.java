package fd.app.business;

import fd.app.dao.RoleRepository;
import fd.app.dao.UserRepository;
import fd.app.domain.AppRole;
import fd.app.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<AppUser> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser addRoleToUser(String username, String rolename) {
        Optional<AppUser> appUserOptional = userRepository.findByUsername(username);
        Optional<AppRole> appRoleOptional = roleRepository.findByRole(rolename);

        appUserOptional.get().getRoles().add(appRoleOptional.get());
        return appUserOptional.get();
    }
}