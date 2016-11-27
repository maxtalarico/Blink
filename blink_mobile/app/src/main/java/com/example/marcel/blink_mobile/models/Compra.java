package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Marcel on 26/11/2016.
 */

public class Compra implements Serializable {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("cliente")
    @Expose
    protected Integer cliente;
    @SerializedName("transacao")
    @Expose
    protected Integer transacao;
    @SerializedName("item")
    @Expose
    protected Integer item;
    @SerializedName("aparelho")
    @Expose
    protected Integer aparelho;
    @SerializedName("estabelecimento")
    @Expose
    protected Integer estabelecimento;
    @SerializedName("desconto")
    @Expose
    protected Float desconto;
    @SerializedName("valorTotal")
    @Expose
    protected Float valorTotal;
    @SerializedName("data")
    @Expose
    protected Date data;
    @SerializedName("qrCode")
    @Expose
    protected String qrCode;
    @SerializedName("cartao")
    @Expose
    protected Integer cartao;
    @SerializedName("status")
    @Expose
    protected String status;

    @Override
    public String toString() {
        return "Compra{" +
                "estabelecimento=" + estabelecimento +
                ", valorTotal=" + valorTotal +
                '}';
    }

    public Integer getAparelho() {
        return aparelho;
    }

    public void setAparelho(Integer aparelho) {
        this.aparelho = aparelho;
    }

    public Integer getCartao() {
        return cartao;
    }

    public void setCartao(Integer cartao) {
        this.cartao = cartao;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Integer estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Integer getId() {
        return id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTransacao() {
        return transacao;
    }

    public void setTransacao(Integer transacao) {
        this.transacao = transacao;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
