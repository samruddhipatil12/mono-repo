package org.dnyanyog.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.encryption.EncDec;
import org.dnyanyog.entity.User;
import org.dnyanyog.repo.DirectoryRepository;
import org.dnyanyog.services.LoginServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class LoginTestControllerJSON {

  @Mock private DirectoryRepository userRepo;

  @Mock private EncDec encrypt;

  @InjectMocks private LoginServiceImp loginService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(loginService).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testValidateUserSuccess() throws Exception {
    LoginRequest request = new LoginRequest();
    request.setMobileNumber(1234567890L);
    request.setPassword("password");

    User user = new User();
    user.setMobileNumber(1234567890L);
    user.setPassword("encryptedPassword");

    when(encrypt.encrypt("password")).thenReturn("encryptedPassword");
    when(userRepo.findByMobileNumber(1234567890L)).thenReturn(Collections.singletonList(user));

    mockMvc
        .perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Login_Success.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Login_Success.getMessage()));
  }

  @Test
  public void testValidateUserFailedInvalidPassword() throws Exception {
    LoginRequest request = new LoginRequest();
    request.setMobileNumber(1234567890L);
    request.setPassword("wrongpassword");

    User user = new User();
    user.setMobileNumber(1234567890L);
    user.setPassword("encryptedPassword");

    when(encrypt.encrypt("wrongpassword")).thenReturn("wrongEncryptedPassword");
    when(userRepo.findByMobileNumber(1234567890L)).thenReturn(Collections.singletonList(user));

    mockMvc
        .perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Login_Failed.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Login_Failed.getMessage()));
  }

  @Test
  public void testValidateUserFailedNoUserFound() throws Exception {
    LoginRequest request = new LoginRequest();
    request.setMobileNumber(1234567890L);
    request.setPassword("password");

    when(userRepo.findByMobileNumber(1234567890L)).thenReturn(Collections.emptyList());

    mockMvc
        .perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Login_Failed.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Login_Failed.getMessage()));
  }
}
