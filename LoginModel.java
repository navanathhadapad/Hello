package com.tagcor.tagcorproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Navanath on 3/13/2018.
 */

public class LoginModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("sid")
    @Expose
    private String sid;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
