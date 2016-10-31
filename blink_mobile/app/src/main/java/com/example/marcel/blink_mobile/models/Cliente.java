package com.example.marcel.blink_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcel on 26/09/2016.
 */
public class Cliente extends Usuario {
    @SerializedName("cartoes")
    @Expose
    protected Cartao[] cartoes;

    public Cartao[] getCartoes() {
        return cartoes;
    }
}
