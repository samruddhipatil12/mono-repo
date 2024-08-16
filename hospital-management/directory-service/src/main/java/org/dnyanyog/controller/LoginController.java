package org.dnyanyog.controller;

import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.dto.LoginResponse;
import org.dnyanyog.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
  @Autowired LoginService loginService;

  @PostMapping(path = "/api/v1/directory/validate")
  public LoginResponse validate(@RequestBody LoginRequest loginRequest) throws Exception {
    return loginService.validateUser(loginRequest);
  }
}
