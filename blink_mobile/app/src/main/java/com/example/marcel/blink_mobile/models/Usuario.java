package com.example.marcel.blink_mobile.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Usuario {

    @SerializedName("usuario")
    @Expose
    private UserData UserData;

    /**
     * No args constructor for use in serialization
     *
     */
    public Usuario() {
    }

    /**
     *
     * @param UserData
     */
    public Usuario(UserData UserData) {
        this.UserData = UserData;
    }

    /**
     *
     * @return
     * The UserData
     */
    public UserData getUserData() {
        return UserData;
    }

    /**
     *
     * @param UserData
     * The UserData
     */
    public void setUserData(UserData UserData) {
        this.UserData = UserData;
    }

    public Usuario withUserData(UserData UserData) {
        this.UserData = UserData;
        return this;
    }
}