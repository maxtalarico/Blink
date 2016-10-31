package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Cartao {
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("nome")
    @Expose
    protected String nome;
    @SerializedName("numero")
    @Expose
    protected int numero;
    @SerializedName("dataVencimento")
    @Expose
    protected Date dataVencimento;
    @SerializedName("codigoSeguranca")
    @Expose
    protected int codigoSeguranca;
    @SerializedName("bandeira")
    @Expose
    protected String bandeira;

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public void setCodigoSeguranca(int codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBandeira() {

        return bandeira;
    }

    public int getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }
}
