package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Marcel on 26/11/2016.
 */

public class Venda implements Serializable {
    @SerializedName("valor")
    @Expose
    protected Float valor;
    @SerializedName("id_estab")
    @Expose
    protected Integer id_estab;
    @SerializedName("id_aparelho")
    @Expose
    protected Integer id_aparelho;

    public Venda(Integer id_aparelho, Integer id_estab, Float valor) {
        this.id_aparelho = id_aparelho;
        this.id_estab = id_estab;
        this.valor = valor;
    }

    public Integer getId_aparelho() {
        return id_aparelho;
    }

    public void setId_aparelho(Integer id_aparelho) {
        this.id_aparelho = id_aparelho;
    }

    public Integer getId_estab() {
        return id_estab;
    }

    public void setId_estab(Integer id_estab) {
        this.id_estab = id_estab;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
}
