package org.dnyanyog.controller;

import org.dnyanyog.dto.DirectoryRequest;
import org.dnyanyog.dto.DirectoryResponse;
import org.dnyanyog.services.DirectoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectoryController {

  @Autowired DirectoryServiceImp directoryService;

  @PostMapping(
      path = "/api/v1/directory/add",
      consumes = {"application/json", "application/xml"},
      produces = {"application/json", "application/xml"})
  public DirectoryResponse addUser(@RequestBody DirectoryRequest request) {
    return directoryService.addUser(request);
  }

  @PutMapping(path = "/api/v1/directory/{userid}")
  public DirectoryResponse updateUser(
      @PathVariable String userid, @RequestBody DirectoryRequest request) {
    return directoryService.updateUser(userid, request);
  }

  @GetMapping(path = "/api/v1/directory/{userid}")
  public DirectoryResponse getSearchUser(@PathVariable String userid) {
    return directoryService.getSearchUser(userid);
  }

  @DeleteMapping(path = "/api/v1/directory/{userid}")
  public DirectoryResponse deleteUser(@PathVariable String userid) {
    return directoryService.deleteUser(userid);
  }
}
