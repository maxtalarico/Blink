package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Marcel on 27/11/2016.
 */

public class Compras {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("cliente")
    @Expose
    protected Cliente cliente;
    @SerializedName("transacao")
    @Expose
    protected Integer transacao;
    @SerializedName("item")
    @Expose
    protected Item[] item;
    @SerializedName("aparelho")
    @Expose
    protected Aparelho aparelho;
    @SerializedName("estabelecimento")
    @Expose
    protected EstabelecimentoComercial estabelecimento;
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
    protected Cartao cartao;
    @SerializedName("status")
    @Expose
    protected String status;

    public Aparelho getAparelho() {
        return aparelho;
    }

    public void setAparelho(Aparelho aparelho) {
        this.aparelho = aparelho;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getDesconto() {
        return desconto;
    }

    public void setDesconto(Float desconto) {
        this.desconto = desconto;
    }

    public EstabelecimentoComercial getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(EstabelecimentoComercial estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
