
package fd.app.rs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fd.app.beans.RegisterForm;
import fd.app.business.AccountService;
import fd.app.domain.AppUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserResource.class)
public class UserResourceTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountService accountService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_signup()throws Exception {
        RegisterForm registerForm = new RegisterForm("", "", "");
        Mockito.when(accountService.saveUser(new AppUser(null, "fode", "bailo", null)))
                .thenReturn(new AppUser(1L, "fode", "bailo", null));

        mvc.perform(post("/signup").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(registerForm)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.username", is("fode")));
    }
}
