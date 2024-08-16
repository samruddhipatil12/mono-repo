package org.dnyanyog.services;

import org.dnyanyog.dto.DirectoryRequest;
import org.dnyanyog.dto.DirectoryResponse;

public interface DirectoryService {
  public DirectoryResponse addUser(DirectoryRequest request);

  public DirectoryResponse updateUser(String userid, DirectoryRequest request);

  public DirectoryResponse getSearchUser(String userid);

  public DirectoryResponse Deleteuser(String userid);
}
