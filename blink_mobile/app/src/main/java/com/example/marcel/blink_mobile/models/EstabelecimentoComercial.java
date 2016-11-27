package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Marcel on 26/09/2016.
 */
public class EstabelecimentoComercial implements Serializable {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("codigoEstabelecimento")
    @Expose
    protected Integer codigoEstabelecimento;
    @SerializedName("nome")
    @Expose
    protected String nome;
    @SerializedName("cnpj")
    @Expose
    protected String cnpj;
    @SerializedName("categoria")
    @Expose
    protected Integer categoria;
    @SerializedName("contaBancaria")
    @Expose
    protected Integer contaBancaria;
    @SerializedName("localizacao")
    @Expose
    protected Integer localizacao;
    @SerializedName("telefoneCom")
    @Expose
    protected String telefoneCom;
    @SerializedName("aparelhos")
    @Expose
    protected Aparelho[] aparelhos;
    @SerializedName("dono")
    @Expose
    protected Integer dono;

    public EstabelecimentoComercial(Integer categoria, String cnpj, Integer codigoEstabelecimento, Integer contaBancaria, Integer id, Integer localizacao, String nome, String telefoneCom, Integer dono) {
        this.categoria = categoria;
        this.cnpj = cnpj;
        this.codigoEstabelecimento = codigoEstabelecimento;
        this.contaBancaria = contaBancaria;
        this.id = id;
        this.localizacao = localizacao;
        this.nome = nome;
        this.telefoneCom = telefoneCom;
        this.dono = dono;
        this.aparelhos = null;
    }

    @Override
    public String toString() {
        return "EstabelecimentoComercial{" +
                "aparelhos=" + Arrays.toString(aparelhos) +
                ", id=" + Integer.toString(id) +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", categoria=" + Integer.toString(categoria) +
                ", contaBancaria=" + Integer.toString(contaBancaria) +
                ", localizacao=" + Integer.toString(localizacao) +
                ", telefoneCom='" + telefoneCom + '\'' +
                ", dono=" + Integer.toString(dono) +
                '}';
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCodigoEstabelecimento(int codigoEstabelecimento) {
        this.codigoEstabelecimento = codigoEstabelecimento;
    }

    public void setContaBancaria(Integer contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public void setLocalizacao(Integer localizacao) {
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

    public Integer getCategoria() {
        return categoria;
    }

    public String getCnpj() {
        return cnpj;
    }

    public int getCodigoEstabelecimento() {
        return codigoEstabelecimento;
    }

    public Integer getContaBancaria() {
        return contaBancaria;
    }

    public Integer getLocalizacao() {
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
