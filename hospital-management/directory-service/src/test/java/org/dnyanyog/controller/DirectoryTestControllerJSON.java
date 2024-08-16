package org.dnyanyog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.DirectoryRequest;
import org.dnyanyog.encryption.EncDec;
import org.dnyanyog.entity.User;
import org.dnyanyog.repo.DirectoryRepository;
import org.dnyanyog.services.DirectoryServiceImp;
import org.dnyanyog.utils.IdGenerator;
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
public class DirectoryTestControllerJSON {

  @Mock private DirectoryRepository userRepo;

  @Mock private EncDec encrypt;

  @InjectMocks private DirectoryServiceImp directoryService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(directoryService).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testAddUser() throws Exception {
    DirectoryRequest request = new DirectoryRequest();
    request.setConfirm("Confirm");
    request.setEmail("Samruddhi@email.com");
    request.setPassword("password");
    request.setUsername("Samruddhi");
    request.setRole("USER");

    User newUser = new User();
    newUser.setUserid(IdGenerator.generateUserId());
    newUser.setConfirm(request.getConfirm());
    newUser.setMobileNumber(request.getMobile_number());
    newUser.setEmail(request.getEmail());
    newUser.setPassword("encryptedPassword");
    newUser.setUsername(request.getUsername());
    newUser.setRole(request.getRole());

    when(encrypt.encrypt(any(String.class))).thenReturn("encryptedPassword");
    when(userRepo.save(any(User.class))).thenReturn(newUser);

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Add_User.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Add_User.getMessage()))
        .andExpect(jsonPath("$.mobile_number").value(newUser.getMobileNumber()))
        .andExpect(jsonPath("$.role").value(newUser.getRole()))
        .andExpect(jsonPath("$.userid").value(newUser.getUserid()));
  }

  @Test
  public void testUpdateUser() throws Exception {
    String userid = "testUserId";
    DirectoryRequest request = new DirectoryRequest();
    request.setConfirm("Confirm");
    request.setEmail("Samruddhi@email.com");
    request.setPassword("password");
    request.setUsername("Samruddhi");
    request.setRole("USER");

    User existingUser = new User();
    existingUser.setUserid(userid);
    existingUser.setConfirm(request.getConfirm());
    existingUser.setMobileNumber(request.getMobile_number());
    existingUser.setEmail(request.getEmail());
    existingUser.setPassword("encryptedPassword");
    existingUser.setUsername(request.getUsername());
    existingUser.setRole(request.getRole());

    when(encrypt.encrypt(any(String.class))).thenReturn("encryptedPassword");
    when(userRepo.findByUserid(userid)).thenReturn(Optional.of(existingUser));
    when(userRepo.save(any(User.class))).thenReturn(existingUser);

    mockMvc
        .perform(
            put("/users/" + userid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Update_User.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Update_User.getMessage()))
        .andExpect(jsonPath("$.mobile_number").value(existingUser.getMobileNumber()))
        .andExpect(jsonPath("$.role").value(existingUser.getRole()))
        .andExpect(jsonPath("$.userid").value(existingUser.getUserid()));
  }

  @Test
  public void testSearchUser() throws Exception {
    String userid = "testUserId";

    User existingUser = new User();
    existingUser.setUserid(userid);
    existingUser.setConfirm("Confirm");
    existingUser.setMobileNumber("1234567890");
    existingUser.setEmail("Samruddhi@email.com");
    existingUser.setPassword("encryptedPassword");
    existingUser.setUsername("Samruddhi");
    existingUser.setRole("USER");

    when(userRepo.findByUserid(userid)).thenReturn(Optional.of(existingUser));

    mockMvc
        .perform(get("/users/" + userid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Search_User.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Search_User.getMessage()))
        .andExpect(jsonPath("$.mobile_number").value(existingUser.getMobileNumber()))
        .andExpect(jsonPath("$.role").value(existingUser.getRole()))
        .andExpect(jsonPath("$.userid").value(userid));
  }

  @Test
  public void testDeleteUser() throws Exception {
    String userid = "testUserId";

    User existingUser = new User();
    existingUser.setUserid(userid);
    existingUser.setConfirm("Confirm");
    existingUser.setMobileNumber("1234567890");
    existingUser.setEmail("Samruddhi@email.com");
    existingUser.setPassword("encryptedPassword");
    existingUser.setUsername("Samruddhi");
    existingUser.setRole("USER");

    when(userRepo.findByUserid(userid)).thenReturn(Optional.of(existingUser));

    mockMvc
        .perform(delete("/users/" + userid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.Delete_User.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.Delete_User.getMessage()))
        .andExpect(jsonPath("$.userid").value(userid));
  }
}
