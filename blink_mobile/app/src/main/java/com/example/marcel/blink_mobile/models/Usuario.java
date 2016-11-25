package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Usuario implements Serializable{

    @SerializedName("usuario")
    @Expose
    private UserData UserData;

    /**
     * No args constructor for use in serialization
     *
     */
    public Usuario(Integer id, Vendedor vendedor, Cliente cliente, Endereco endereco, String email, String senha, String nome) {
        UserData userData = new UserData(id, vendedor, cliente, endereco, email, senha, nome);

        this.setUserData(userData);
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