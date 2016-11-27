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
    protected Integer id;
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
    @SerializedName("serial")
    @Expose
    protected String serial;
    @SerializedName("proprietario")
    @Expose
    protected Integer proprietario;

    public Aparelho(Date dataAtivacao, Integer id, String nome, String serial, String status, Date ultimoUso, Integer proprietario) {
        this.dataAtivacao = dataAtivacao;
        this.id = id;
        this.nome = nome;
        this.serial = serial;
        this.status = status;
        this.ultimoUso = ultimoUso;
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Aparelho{" +
                ", nome='" + nome + '\'' +
                ", status='" + status + '\'' +
                ", serial='" + serial + '\'' +
                ", proprietario=" + Integer.toString(proprietario) +
                '}';
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

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
