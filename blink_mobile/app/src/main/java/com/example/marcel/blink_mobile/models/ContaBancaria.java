package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Marcel on 26/09/2016.
 */
public class ContaBancaria implements Serializable {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("banco")
    @Expose
    protected Integer banco;
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
    protected Integer tipoConta;
    @SerializedName("titular")
    @Expose
    protected Integer titular;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public ContaBancaria(String agencia, Integer banco, String digitoAgencia, String digitoConta, Integer id, String numeroConta, Integer tipoConta, Integer titular) {
        this.agencia = agencia;
        this.banco = banco;
        this.digitoAgencia = digitoAgencia;
        this.digitoConta = digitoConta;
        this.id = id;
        this.numeroConta = numeroConta;
        this.tipoConta = tipoConta;
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
                "agencia='" + agencia + '\'' +
                ", id=" + id +
                ", banco=" + banco +
                ", digitoAgencia='" + digitoAgencia + '\'' +
                ", numeroConta='" + numeroConta + '\'' +
                ", digitoConta='" + digitoConta + '\'' +
                ", tipoConta=" + tipoConta +
                ", titular=" + titular +
                '}';
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setBanco(Integer banco) {
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

    public void setTipoConta(Integer tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getAgencia() {

        return agencia;
    }

    public Integer getBanco() {
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

    public Integer getTipoConta() {
        return tipoConta;
    }
}
