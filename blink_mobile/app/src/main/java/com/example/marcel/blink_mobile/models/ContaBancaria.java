package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcel on 26/09/2016.
 */
public class ContaBancaria {
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("banco")
    @Expose
    protected String banco;
    @SerializedName("agencia")
    @Expose
    protected String agencia;
    @SerializedName("digitoAgencia")
    @Expose
    protected String digitoAgencia;
    @SerializedName("numeroConta")
    @Expose
    protected String numeroConta;
    @SerializedName("digitoConta")
    @Expose
    protected String digitoConta;
    @SerializedName("tipoConta")
    @Expose
    protected String tipoConta;

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getAgencia() {

        return agencia;
    }

    public String getBanco() {
        return banco;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public int getId() {
        return id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }
}
