package org.dnyanyog.dto;

import org.springframework.stereotype.Component;

@Component
public class DirectoryResponse {

  private String status;
  private String message;
  private long mobile_number;
  private String role;
  private String password;
  private String confirm;
  private String email;
  private String userid;
  private String username;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public static DirectoryResponse getInstance() {
    return new DirectoryResponse();
  }
}
