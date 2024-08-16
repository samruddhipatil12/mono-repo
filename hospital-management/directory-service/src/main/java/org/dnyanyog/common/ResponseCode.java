package org.dnyanyog.common;

public enum ResponseCode {
  Login_Success("Success", "Login Successfully!!"),
  Login_Failed("Fail", "Login Failed."),

  Add_User("Success", "User added successfully!!"),
  Add_User_Failed("Fail", "Unable to add user."),

  Update_User("Success", "User updated successfully!!"),
  Update_User_Failed("Fail", "Update User Unsuccessfully."),

  Search_User("Success", "User are found!!"),
  Search_User_Failed("Fail", "No user found."),

  Delete_User("Success", "User deleted successfully!!"),
  Delete_User_Failed("Fail", "Failed to remove the user.");

  private String message;
  private String status;

  private ResponseCode(String message, String status) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
