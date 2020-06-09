
package fd.app.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fd.app.beans.RegisterForm;
import fd.app.business.AccountService;
import fd.app.dao.ProductRepository;
import fd.app.dao.RoleRepository;
import fd.app.domain.AppUser;
import fd.app.rs.UserResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserResource.class)
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
public class UserResourceTests {
    @Autowired
    private MockMvc mvc;@Qualifier("userDetailsServiceImpl")
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_signup() throws Exception {
        RegisterForm registerForm = new RegisterForm("fode", "bailo", "bailo");

        Mockito.when(accountService.saveUser(new AppUser(null, "fode", "bailo", null)))
                .thenReturn(new AppUser(null, "fode", "bailo", null));

        mvc.perform(post("/signup").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(registerForm)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.username", is("fode")));
    }
}
