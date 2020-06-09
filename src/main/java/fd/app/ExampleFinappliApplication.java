package fd.app;

import fd.app.dao.ProductRepository;
import fd.app.dao.RoleRepository;
import fd.app.domain.AppRole;
import fd.app.domain.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Arrays;

@EnableWebSecurity
@SpringBootApplication
public class ExampleFinappliApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleFinappliApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, RoleRepository roleRepository) {
        return args -> {
            productRepository.saveAll(Arrays.asList(
                    new Product("PC-100", "HP 960", "", 430, 7),
                    new Product("PC-200", "Acer A9000", "", 320, 4),
                    new Product("PC-134", "HP Laptop H900", "Machine HP", 1800.0, 10)));

            roleRepository.saveAll(Arrays.asList(new AppRole(null, "USER"), new AppRole(null, "ADMIN")));
        };
    }
}
