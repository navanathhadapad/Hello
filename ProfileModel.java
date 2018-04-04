package com.tagcor.tagcorproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

@SerializedName("user")
@Expose
private UserModel user;

public UserModel getUser() {
return user;
}

public void setUser(UserModel user) {
this.user = user;
}

}