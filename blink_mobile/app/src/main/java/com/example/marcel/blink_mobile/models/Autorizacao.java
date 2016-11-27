package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Marcel on 26/11/2016.
 */

public class Autorizacao implements Serializable {
    @SerializedName("id_cliente")
    @Expose
    protected Integer id_cliente;
    @SerializedName("id_cartao")
    @Expose
    protected Integer id_cartao;
    @SerializedName("id_compra")
    @Expose
    protected Integer id_compra;
    @SerializedName("acao")
    @Expose
    protected String acao;

    public Autorizacao(String acao, Integer id_cartao, Integer id_cliente, Integer id_compra) {
        this.acao = acao;
        this.id_cartao = id_cartao;
        this.id_cliente = id_cliente;
        this.id_compra = id_compra;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Integer getId_cartao() {
        return id_cartao;
    }

    public void setId_cartao(Integer id_cartao) {
        this.id_cartao = id_cartao;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_compra() {
        return id_compra;
    }

    public void setId_compra(Integer id_compra) {
        this.id_compra = id_compra;
    }
}
