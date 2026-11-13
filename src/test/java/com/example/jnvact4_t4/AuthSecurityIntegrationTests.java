package com.example.jnvact4_t4;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.jnvact4_t4.repository.usuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AuthSecurityIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usuarioRepository usuarioRepository;

    @BeforeEach
    void cleanUsers() {
        usuarioRepository.deleteAll();
    }

    @Test
    void protectedCrudRequiresToken() throws Exception {
        mockMvc.perform(get("/api/repartidores"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerDoesNotExposePassword() throws Exception {
        String username = uniqueUsername();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "password": "password123"
                                }
                                """.formatted(username)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void invalidCredentialsReturnUnauthorized() throws Exception {
        String username = uniqueUsername();
        register(username);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "password": "wrong-password"
                                }
                                """.formatted(username)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void validTokenAllowsCrudAccess() throws Exception {
        String username = uniqueUsername();
        register(username);
        String token = login(username);

        mockMvc.perform(get("/api/repartidores")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private void register(String username) throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "username": "%s",
                          "password": "password123"
                        }
                        """.formatted(username)))
                .andExpect(status().isCreated());
    }

    private String login(String username) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "password": "password123"
                                }
                                """.formatted(username)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        return response.substring(
                response.indexOf("\"token\":\"") + 9,
                response.indexOf("\"", response.indexOf("\"token\":\"") + 9));
    }

    private String uniqueUsername() {
        return "user-" + UUID.randomUUID();
    }
}
