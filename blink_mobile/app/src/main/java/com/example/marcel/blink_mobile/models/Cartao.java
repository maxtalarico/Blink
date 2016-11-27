package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Cartao implements Serializable {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("nome")
    @Expose
    protected String nome;
    @SerializedName("numero")
    @Expose
    protected String numero;
    @SerializedName("dataVencimento")
    @Expose
    protected String dataVencimento;
    @SerializedName("codigoSeguranca")
    @Expose
    protected int codigoSeguranca;
    @SerializedName("bandeira")
    @Expose
    protected String bandeira;
    @SerializedName("proprietario")
    @Expose
    protected Integer proprietario;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;


    @Override
    public String toString() {
        return "Cartao{" +
                "bandeira='" + bandeira + '\'' +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", numero=" + numero +
                ", codigoSeguranca=" + codigoSeguranca +
                ", proprietario=" + proprietario +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    public Cartao(String bandeira, int codigoSeguranca, String dataVencimento, Integer id, String nome, String numero, Integer proprietario/*, Cliente proprietario*/) {
        this.bandeira = bandeira;
        this.codigoSeguranca = codigoSeguranca;
        this.dataVencimento = dataVencimento;
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.proprietario = proprietario;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public void setCodigoSeguranca(int codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBandeira() {

        return bandeira;
    }

    public int getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }
}
