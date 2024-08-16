package org.dnyanyog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class DirectoryData {

  @NotNull(message = "User ID is mandatory")
  private long userid;

  @NotBlank(message = "Username should not be blank")
  @Size(max = 50, message = "Username length should be at most 50 characters")
  private String username;

  @NotBlank(message = "Email should not be blank")
  @Email(message = "Email should be valid")
  private String email;

  @NotNull(message = "Mobile number is mandatory")
  @Pattern(regexp = "\\d{10}", message = "Mobile number should be a 10-digit number")
  private long mobile_number;

  @NotBlank(message = "Role should not be blank")
  private String role;

  @NotBlank(message = "Password should not be blank")
  @Size(min = 6, message = "Password should have at least 6 characters")
  private String password;

  @NotBlank(message = "Confirm password should not be blank")
  private String confirm;

  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getMobile_number() {
    return mobile_number;
  }

  public void setMobile_number(long mobile_number) {
    this.mobile_number = mobile_number;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm() {
    return confirm;
  }

  public void setConfirm(String confirm) {
    this.confirm = confirm;
  }
}
