package com;

import com.domains.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void contextLoads() {
    }

    // breakpoint "/users/search/findByLogin" with exist login returns "Ok" status and JSON with correct login
    @Test
    void breakpointFindByLoginWithExistLoginReturnsJsonWithCorrectLogin() throws Exception {
        String login = "exist";
        User user = new User(login);
        entityManager.persist(user);
        entityManager.flush();

        this.mockMvc.perform(get(String.format("/users/search/findByLogin?login=%s", login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value(login));
    }

    //breakpoint "/users/search/findByLogin" with nonexistent login returns returns "Not Found" status
    // and returned JSON does not contains such login
    @Test
    void breakpointFindByLoginWithNonexistentLoginReturnsEmptyJson() throws Exception {
        String login = "nonexistent";

        this.mockMvc.perform(get(String.format("/users/search/findByLogin?login=%s", login)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(login).doesNotHaveJsonPath());
    }
}
