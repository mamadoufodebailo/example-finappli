package fd.app.dao;

import fd.app.domain.AppRole;
import fd.app.domain.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
public class SecurityDaoTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void test_user() {
        entityManager.merge(new AppUser(null, "fode", "123", null));

        Optional<AppUser> appUserOptional = userRepository.findByUsername("fode");
        assertTrue(appUserOptional.isPresent());
    }

    @Test
    void test_role() {
        entityManager.merge(new AppRole(null, "MANAGER"));

        Optional<AppRole> appRoleOptional = roleRepository.findByRole("MANAGER");
        assertTrue(appRoleOptional.isPresent());
    }

}
