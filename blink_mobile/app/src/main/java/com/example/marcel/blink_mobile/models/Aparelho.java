package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Marcel on 26/09/2016.
 */

public class Aparelho implements Serializable {
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("nome")
    @Expose
    protected String nome;
    @SerializedName("dataAtivacao")
    @Expose
    protected Date dataAtivacao;
    @SerializedName("ultimoUso")
    @Expose
    protected Date ultimoUso;
    @SerializedName("status")
    @Expose
    protected String status;

    public void setDataAtivacao(Date dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUltimoUso(Date ultimoUso) {
        this.ultimoUso = ultimoUso;
    }

    public Date getUltimoUso() {

        return ultimoUso;
    }

    public Date getDataAtivacao() {
        return dataAtivacao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status;
    }
}
