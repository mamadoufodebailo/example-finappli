package fd.app.business;

import fd.app.domain.AppRole;
import fd.app.domain.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTests {
    @MockBean
    private AccountService accountService;

    @Test
    void test_getUser() {
        Mockito.when(accountService.findUserByUsername("fode"))
                .thenReturn(Optional.of(new AppUser(1L, "fode", "bailo",null)));

        Optional<AppUser> userOptional = accountService.findUserByUsername("fode");

        assertTrue(userOptional.isPresent());
        assertEquals("bailo", userOptional.get().getPassword());
    }

    @Test
    void test_saveRole() {
        Mockito.when(accountService.saveRole(new AppRole(null, "ADMIN")))
                .thenReturn(new AppRole(1L, "ADMIN"));

        AppRole role = accountService.saveRole(new AppRole(null, "ADMIN"));
        assertNotNull(role);
        assertEquals("ADMIN", role.getRole());
    }

    @Test
    void test_saveUser() {
        Mockito.when(accountService.saveUser(new AppUser(null, "fode", "bailo",null)))
                .thenReturn(new AppUser(1L, "fode", "bailo", null));

        AppUser user = accountService.saveUser(new AppUser(null, "fode", "bailo", null));
        assertNotNull(user);
        assertEquals("bailo", user.getPassword());
    }

    @Test
    void test_userToRole() {
        Mockito.when(accountService.addRoleToUser("fode", "ADMIN"))
                .thenReturn(new AppUser(1L, "fode", "bailo",
                        Collections.singletonList(new AppRole(1L, "ADMIN"))));

        AppUser user = accountService.addRoleToUser("fode", "ADMIN");
        assertNotNull(user);
        assertTrue(user.getRoles().stream().findFirst().isPresent());
        assertEquals("ADMIN", user.getRoles().stream().findFirst().get().getRole());
    }
}
