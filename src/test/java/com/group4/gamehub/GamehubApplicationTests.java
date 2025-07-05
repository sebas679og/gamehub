package com.group4.gamehub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.gamehub.dto.requests.auth.Login;
import com.group4.gamehub.dto.requests.auth.Register;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class GamehubApplicationTests {

  @Test
  void contextLoads() {}

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private UserRepository userRepository;

  private final Register register =
      Register.builder().username("sebas").email("sebas@example.com").password("123456").build();

  private final Login login = Login.builder().username("sebas").password("123456").build();

  private String jwtToken;

  @BeforeEach
  void setUp() throws Exception {
    if (userRepository.findByUsername("sebas").isEmpty()) {
      String response =
          mockMvc
              .perform(
                  post("/api/auth/register")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(register)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.token").exists())
              .andReturn()
              .getResponse()
              .getContentAsString();

      jwtToken = objectMapper.readTree(response).get("token").asText();
    } else {
      String response =
          mockMvc
              .perform(
                  post("/api/auth/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(login)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.token").exists())
              .andReturn()
              .getResponse()
              .getContentAsString();

      jwtToken = objectMapper.readTree(response).get("token").asText();
    }
  }

  @Test
  void loginWithAdminUser_ReturnsOk() throws Exception {
    Login adminLogin = Login.builder().username("admin").password("admin123").build();

    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminLogin)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  void registerExistingEmail_ReturnsConflict() throws Exception {
    Register duplicateEmail =
        Register.builder()
            .username("otheruser")
            .email("sebas@example.com")
            .password("abc123")
            .build();

    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateEmail)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value(409));
  }

  @Test
  void loginWithInvalidUser_ReturnsBadRequest() throws Exception {
    Login invalidLogin = Login.builder().username("notexists").password("123456").build();

    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLogin)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400));
  }

  @Test
  void accessProtectedEndpoint_WithValidToken_Returns200() throws Exception {
    mockMvc
        .perform(get("/api/users/me").header("Authorization", "Bearer " + jwtToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("sebas"));
  }

  @Test
  void accessProtectedEndpoint_WithoutToken_ReturnsUnauthorized() throws Exception {
    mockMvc
        .perform(get("/api/users/me"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value(401));
  }

  @Test
  void accessProtectedEndpoint_WithInvalidToken_ReturnsUnauthorized() throws Exception {
    String fakeToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            + ".eyJzdWIiOiJzZWJhcyIsInJvbGUiOiJST0xFX1BMQVlFUiIsImlhdCI6MTc1MDUzOTE2NSwiZXhwIjoxNzUwNTQyNzY1fQ"
            + ".3bapsyVTgndX7L0d4005SKliCR6sBtGRn59dyMZgxk4";

    mockMvc
        .perform(get("/api/users/me").header("Authorization", "Bearer " + fakeToken))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value(401));
  }

  @Test
  void getUserByUsername_ReturnsPublicUserResponse() throws Exception {
    String username = "testuser";
    if (userRepository.findByUsername(username).isEmpty()) {
      Register register =
          Register.builder()
              .username(username)
              .email("testuser@example.com")
              .password("123456")
              .build();

      mockMvc
          .perform(
              post("/api/auth/register")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(register)))
          .andExpect(status().isCreated());
    }

    // Probar el endpoint GET /api/users/{id}
    mockMvc
        .perform(get("/api/users/" + username).header("Authorization", "Bearer " + jwtToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(username))
        .andExpect(jsonPath("$.role").value(Role.PLAYER.name()))
        .andExpect(jsonPath("$.rank").value((Object) null))
        .andExpect(jsonPath("$.points").value((Object) null));
  }
}
