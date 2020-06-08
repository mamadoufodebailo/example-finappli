package fd.app.business;

import fd.app.domain.AppRole;
import fd.app.domain.AppUser;

import java.util.Optional;

public interface AccountService {
    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole role);
    Optional<AppUser> findUserByUsername(String username);
    AppUser addRoleToUser(String username,String role);
}
