package com.tagcor.tagcorproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

@SerializedName("fname")
@Expose
private String fname;
@SerializedName("lname")
@Expose
private String lname;
@SerializedName("personal_profile")
@Expose
private String personalProfile;
@SerializedName("user_profession")
@Expose
private String userProfession;
@SerializedName("job_profile_title")
@Expose
private String jobProfileTitle;

public String getFname() {
return fname;
}

public void setFname(String fname) {
this.fname = fname;
}

public String getLname() {
return lname;
}

public void setLname(String lname) {
this.lname = lname;
}

    public String getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }

    public String getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(String userProfession) {
        this.userProfession = userProfession;
    }

    public String getJobProfileTitle() {
        return jobProfileTitle;
    }

    public void setJobProfileTitle(String jobProfileTitle) {
        this.jobProfileTitle = jobProfileTitle;
    }

}