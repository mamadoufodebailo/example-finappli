package fd.app.dao;

import fd.app.domain.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByRole(String role);
}
