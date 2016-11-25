package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Cliente implements Serializable {
    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("dataNascimento")
    @Expose
    protected Date dataNascimento;
    @SerializedName("nome")
    @Expose
    protected String nome;
    @SerializedName("cpf")
    @Expose
    protected String cpf;
    @SerializedName("telefoneRes")
    @Expose
    protected String telefoneRes;
    @SerializedName("celular")
    @Expose
    protected String celular;
    @SerializedName("cartoes")
    @Expose
    protected Cartao[] cartoes;

    public Cliente(Integer id, String celular, String cpf, Date dataNascimento, String nome, String telefoneRes) {
        this.id = id;
        this.celular = celular;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        this.telefoneRes = telefoneRes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cartao[] getCartoes() {
        return cartoes;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefoneRes() {
        return telefoneRes;
    }

    public void setTelefoneRes(String telefoneRes) {
        this.telefoneRes = telefoneRes;
    }

    public void setCartoes(Cartao[] cartoes) {
        this.cartoes = cartoes;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cartoes=" + Arrays.toString(cartoes) +
                ", id=" + id +
                ", dataNascimento=" + dataNascimento +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefoneRes='" + telefoneRes + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }
}
