package org.dnyanyog.services;

import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
  public LoginResponse validateUser(LoginRequest loginrequest) throws Exception;
}
