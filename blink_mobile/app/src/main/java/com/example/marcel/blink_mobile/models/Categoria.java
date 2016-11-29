package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Marcel on 26/11/2016.
 */

public class Categoria implements Serializable {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("nome")
    @Expose
    protected String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
