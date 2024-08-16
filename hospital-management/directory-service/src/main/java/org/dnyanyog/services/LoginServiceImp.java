package org.dnyanyog.services;

import java.util.List;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.dto.LoginResponse;
import org.dnyanyog.encryption.EncDec;
import org.dnyanyog.entity.User;
import org.dnyanyog.repo.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {

  @Autowired private DirectoryRepository userRepo;

  @Autowired private EncDec encrypt;

  @Override
  public LoginResponse validateUser(LoginRequest loginRequest) throws Exception {
    LoginResponse response = new LoginResponse();
    List<User> users = userRepo.findByMobileNumber(loginRequest.getMobileNumber());

    if (users.size() == 1) {
      User user = users.get(0);
      String encryptedPassword = user.getPassword();
      String providedEncryptedPassword = encrypt.encrypt(loginRequest.getPassword());

      if (providedEncryptedPassword.equalsIgnoreCase(encryptedPassword)) {
        response.setStatus(ResponseCode.Login_Success.getStatus());
        response.setMessage(ResponseCode.Login_Success.getMessage());
      } else {
        response.setStatus(ResponseCode.Login_Failed.getStatus());
        response.setMessage(ResponseCode.Login_Failed.getMessage());
      }
    } else {
      response.setStatus(ResponseCode.Login_Failed.getStatus());
      response.setMessage(ResponseCode.Login_Failed.getMessage());
    }

    return response;
  }
}
