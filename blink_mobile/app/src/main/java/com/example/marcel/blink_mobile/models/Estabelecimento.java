package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Estabelecimento {
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("codigoEstabelecimento")
    @Expose
    protected int codigoEstabelecimento;
    @SerializedName("nome")
    @Expose
    protected String nome;
    @SerializedName("cnpj")
    @Expose
    protected String cnpj;
    @SerializedName("categoria")
    @Expose
    protected String categoria;
    @SerializedName("contaBancaria")
    @Expose
    protected ContaBancaria contaBancaria;
    @SerializedName("localizacao")
    @Expose
    protected Endereco localizacao;
    @SerializedName("telefoneCom")
    @Expose
    protected String telefoneCom;
    @SerializedName("aparelhos")
    @Expose
    protected Aparelho[] aparelhos;


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCodigoEstabelecimento(int codigoEstabelecimento) {
        this.codigoEstabelecimento = codigoEstabelecimento;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public void setLocalizacao(Endereco localizacao) {
        this.localizacao = localizacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefoneCom(String telefoneCom) {
        this.telefoneCom = telefoneCom;
    }

    public Aparelho[] getAparelhos() {

        return aparelhos;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCnpj() {
        return cnpj;
    }

    public int getCodigoEstabelecimento() {
        return codigoEstabelecimento;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public Endereco getLocalizacao() {
        return localizacao;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefoneCom() {
        return telefoneCom;
    }

    public int getId() {
        return id;
    }
}
