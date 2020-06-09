package fd.app.rs;

import fd.app.beans.RegisterForm;
import fd.app.business.AccountService;
import fd.app.domain.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin("*")
@RestController
public class UserResource {
    private AccountService accountService;

    public UserResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> signUp(@RequestBody RegisterForm data) {
        String username = data.getUsername();
        Optional<AppUser> userOptional = accountService.findUserByUsername(username);

        if(userOptional.isPresent())
            throw new RuntimeException("This user already exists, Try with an other username");

        String password = data.getPassword();
        String repassword = data.getRepassword();

        if(!password.equals(repassword))
            throw new RuntimeException("You must confirm your password");

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);

        accountService.saveUser(user);
        accountService.addRoleToUser(username, "USER");

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
