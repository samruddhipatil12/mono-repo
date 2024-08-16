package org.dnyanyog.services;

import java.util.Optional;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.DirectoryRequest;
import org.dnyanyog.dto.DirectoryResponse;
import org.dnyanyog.encryption.EncDec;
import org.dnyanyog.entity.User;
import org.dnyanyog.repo.DirectoryRepository;
import org.dnyanyog.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryServiceImp {

  @Autowired private DirectoryRepository userRepo;

  @Autowired private EncDec encrypt;

  private String generateUserId() {
    return "USR"
        + IdGenerator
            .generateUserId(); // Assuming you have a method to generate a random alphanumeric
    // string
  }

  public DirectoryResponse addUser(DirectoryRequest request) {
    DirectoryResponse response = new DirectoryResponse();
    try {
      User user = new User();
      user.setConfirm(request.getConfirm());
      user.setMobileNumber(request.getMobile_number());
      user.setEmail(request.getEmail());
      user.setPassword(encrypt.encrypt(request.getPassword()));
      user.setUsername(request.getUsername());
      user.setRole(request.getRole());

      // Generate user ID
      String userId = generateUserId();
      user.setUserid(userId);

      user = userRepo.save(user);

      response.setMobile_number(user.getMobileNumber());
      response.setRole(user.getRole());
      response.setPassword(user.getPassword());
      response.setConfirm(user.getConfirm());
      response.setEmail(user.getEmail());
      response.setUserid(user.getUserid());

      response.setMessage(ResponseCode.Add_User.getMessage());
      response.setStatus(ResponseCode.Add_User.getStatus());

    } catch (Exception e) {
      response.setStatus(ResponseCode.Add_User_Failed.getStatus());
      response.setMessage(ResponseCode.Add_User_Failed.getMessage());
    }
    return response;
  }

  public DirectoryResponse updateUser(String userid, DirectoryRequest request) {
    DirectoryResponse response = new DirectoryResponse();
    try {
      Optional<User> receiveData = userRepo.findByUserid(userid);
      if (receiveData.isPresent()) {
        User user = receiveData.get();
        user.setConfirm(request.getConfirm());
        user.setMobileNumber(request.getMobile_number());
        user.setEmail(request.getEmail());
        user.setPassword(encrypt.encrypt(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setRole(request.getRole());

        user = userRepo.save(user);

        response.setMessage(ResponseCode.Update_User.getMessage());
        response.setStatus(ResponseCode.Update_User.getStatus());
        response.setMobile_number(user.getMobileNumber());
        response.setRole(user.getRole());
        response.setPassword(user.getPassword());
        response.setConfirm(user.getConfirm());
        response.setEmail(user.getEmail());
        response.setUserid(user.getUserid());
      } else {
        response.setMessage(ResponseCode.Update_User_Failed.getMessage());
        response.setStatus(ResponseCode.Update_User_Failed.getStatus());
        response.setUserid(userid);
      }
    } catch (Exception e) {
      response.setMessage(ResponseCode.Update_User_Failed.getMessage());
      response.setStatus(ResponseCode.Update_User_Failed.getStatus());
      response.setUserid(userid);
    }
    return response;
  }

  public DirectoryResponse getSearchUser(String userid) {
    DirectoryResponse response = new DirectoryResponse();
    try {
      Optional<User> receiveData = userRepo.findByUserid(userid);
      if (receiveData.isPresent()) {
        User user = receiveData.get();
        response.setMessage(ResponseCode.Search_User.getMessage());
        response.setStatus(ResponseCode.Search_User.getStatus());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMobile_number(user.getMobileNumber());
        response.setRole(user.getRole());
        response.setPassword(user.getPassword());
        response.setConfirm(user.getConfirm());
        response.setUserid(userid);
      } else {
        response.setMessage(ResponseCode.Search_User_Failed.getMessage());
        response.setStatus(ResponseCode.Search_User_Failed.getStatus());
        response.setUserid(userid);
      }
    } catch (Exception e) {
      response.setMessage(ResponseCode.Search_User_Failed.getMessage());
      response.setStatus(ResponseCode.Search_User_Failed.getStatus());
      response.setUserid(userid);
    }
    return response;
  }

  public DirectoryResponse deleteUser(String userid) {
    DirectoryResponse response = new DirectoryResponse();
    try {
      Optional<User> receiveData = userRepo.findByUserid(userid);
      if (receiveData.isPresent()) {
        userRepo.findByUserid(userid);
        response.setMessage(ResponseCode.Delete_User.getMessage());
        response.setStatus(ResponseCode.Delete_User.getStatus());
        response.setUserid(userid);
      } else {
        response.setMessage(ResponseCode.Delete_User_Failed.getMessage());
        response.setStatus(ResponseCode.Delete_User_Failed.getStatus());
        response.setUserid(userid);
      }
    } catch (Exception e) {
      response.setMessage(ResponseCode.Delete_User_Failed.getMessage());
      response.setStatus(ResponseCode.Delete_User_Failed.getStatus());
      response.setUserid(userid);
    }
    return response;
  }
}
