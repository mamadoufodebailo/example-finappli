package fd.app;

import fd.app.business.AccountService;
import fd.app.dao.ProductRepository;
import fd.app.domain.AppRole;
import fd.app.domain.AppUser;
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
    CommandLineRunner start(ProductRepository productRepository, AccountService accountService) {
        return args -> {
            productRepository.saveAll(Arrays.asList(
                    new Product("PC-100", "HP 960", "", 430, 7),
                    new Product("PC-200", "Acer A9000", "", 320, 4),
                    new Product("PC-300", "Samsung S7000", "", 150, 5)));

            accountService.saveRole(new AppRole(null,"USER"));
            accountService.saveRole(new AppRole(null,"ADMIN"));
            accountService.saveUser(new AppUser(null,"user","passer123", null));
            accountService.saveUser(new AppUser(null,"admin","passer123", null));

            accountService.addRoleToUser("user", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
        };
    }
}
