package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Vendedor implements Serializable {
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
    @SerializedName("telefoneCom")
    @Expose
    protected String telefoneCom;
    @SerializedName("estabelecimentos")
    @Expose
    protected EstabelecimentoComercial[] estabelecimentoComercials;

    public Vendedor(Integer id, String celular, String cpf, Date dataNascimento, String nome, String telefoneCom, String telefoneRes) {
        this.id = id;
        this.celular = celular;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        this.telefoneCom = telefoneCom;
        this.telefoneRes = telefoneRes;
    }

    public void setTelefoneCom(String telefoneCom) {
        this.telefoneCom = telefoneCom;
    }

    public ContaBancaria[] getContasBancarias() {

        return contasBancarias;
    }

    public EstabelecimentoComercial[] getEstabelecimentoComercials() {
        return estabelecimentoComercials;
    }

    public String getTelefoneCom() {
        return telefoneCom;
    }

    protected ContaBancaria[] contasBancarias;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setContasBancarias(ContaBancaria[] contasBancarias) {
        this.contasBancarias = contasBancarias;
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

    public void setEstabelecimentoComercials(EstabelecimentoComercial[] estabelecimentoComercials) {
        this.estabelecimentoComercials = estabelecimentoComercials;
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

    @Override
    public String toString() {
        return "Vendedor{" +
                "celular='" + celular + '\'' +
                ", id=" + id +
                ", dataNascimento=" + dataNascimento +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefoneRes='" + telefoneRes + '\'' +
                ", telefoneCom='" + telefoneCom + '\'' +
                ", estabelecimentos=" + Arrays.toString(estabelecimentoComercials) +
                ", contasBancarias=" + Arrays.toString(contasBancarias) +
                '}';
    }
}
