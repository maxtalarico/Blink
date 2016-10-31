package com.example.marcel.blink_mobile.models;

import android.util.Log;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Endereco {
    /*public class EstadosCidades<String> {
        public final String estado;
        public final String[] cidades;

        public EstadosCidades(String estado, String[] cidades) {
            this.estado = estado;
            this.cidades = cidades;
        }
    }

    EstadosCidades estadosCidades = new EstadosCidades("São Paulo");*/
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("logradouro")
    @Expose
    protected String logradouro;
    @SerializedName("numero")
    @Expose
    protected String numero;
    @SerializedName("bairro")
    @Expose
    protected String bairro;
    @SerializedName("cidade")
    @Expose
    protected int cidade;
    @SerializedName("estado")
    @Expose
    protected int estado;

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(int cidade) {
        this.cidade = cidade;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


    public String getBairro() {

        return bairro;
    }

    public int getCidade() {
        return cidade;
    }

    public int getEstado() {
        return estado;
    }

    public ArrayList<String> getEstados() {

        ArrayList<String> estados = new ArrayList<String>();

        return estados;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "bairro='" + bairro + '\'' +
                ", id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", cidade=" + cidade +
                ", estado=" + estado +
                '}';
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public int getId() {
        return id;
    }
}
