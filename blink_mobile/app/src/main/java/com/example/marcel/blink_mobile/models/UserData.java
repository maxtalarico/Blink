package com.example.marcel.blink_mobile.models;

/**
 * Created by Marcel on 04/10/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserData implements Serializable {
    @SerializedName("vendedor")
    @Expose
    private Vendedor vendedor;
    @SerializedName("cliente")
    @Expose
    private Cliente cliente;
    @SerializedName("endereco")
    @Expose
    private Endereco endereco;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("senha")
    @Expose
    private String senha;
    @SerializedName("isAdmin")
    @Expose
    private Object isAdmin;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("gravatar")
    @Expose
    private String gravatar;
    @SerializedName("gravatarUrl")
    @Expose
    private String gravatarUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserData(Integer id, Vendedor vendedor, Cliente cliente, Endereco endereco, String email, String senha, String nome) {
        this.id = id;
        this.setVendedor(vendedor);
        this.setCliente(cliente);
        this.setEndereco(endereco);
        this.setEmail(email);
        this.setSenha(senha);
        this.setNome(nome);
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param vendedor
     * @param token
     * @param email
     * @param cliente
     * @param createdAt
     * @param gravatar
     * @param nome
     * @param isAdmin
     * @param gravatarUrl
     * @param endereco
     */
    public UserData(Vendedor vendedor, Cliente cliente, Endereco endereco, Integer id, String email, Object isAdmin, String createdAt, String updatedAt, String token, String nome, String gravatar, String gravatarUrl) {
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.endereco = endereco;
        this.id = id;
        this.email = email;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.token = token;
        this.nome = nome;
        this.gravatar = gravatar;
        this.gravatarUrl = gravatarUrl;
    }

    /**
     *
     * @return
     * The vendedor
     */
    public Vendedor getVendedor() {
        return vendedor;
    }

    /**
     *
     * @param vendedor
     * The vendedor
     */
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public UserData withVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        return this;
    }

    /**
     *
     * @return
     * The cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     *
     * @param cliente
     * The cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public UserData withCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    @Override
    public String toString() {
        String strVendedor = "null";
        String strCliente = "null";
        if(!(vendedor == null)) {
            strVendedor = vendedor.toString();
        }

        if(!(cliente == null)) {
            strCliente = cliente.toString();
        }
        return "UserData{" +
                "createdAt='" + createdAt + '\'' +
                ", vendedor='" + strVendedor + '\'' +
                ", cliente='" + strCliente + '\'' +
                ", endereco=" + endereco.toString() +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", updatedAt='" + updatedAt + '\'' +
                ", token='" + token + '\'' +
                ", nome='" + nome + '\'' +
                ", gravatar='" + gravatar + '\'' +
                ", gravatarUrl='" + gravatarUrl + '\'' +
                '}';
    }

    /**
     *
     * @return
     * The endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     *
     * @param endereco
     * The endereco
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public UserData withEndereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public UserData withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public UserData withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     *
     * @return
     * The isAdmin
     */
    public Object getIsAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param isAdmin
     * The isAdmin
     */
    public void setIsAdmin(Object isAdmin) {
        this.isAdmin = isAdmin;
    }

    public UserData withIsAdmin(Object isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserData withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserData withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    public UserData withToken(String token) {
        this.token = token;
        return this;
    }

    /**
     *
     * @return
     * The nome
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     * The nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public UserData withNome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     *
     * @return
     * The gravatar
     */
    public String getGravatar() {
        return gravatar;
    }

    /**
     *
     * @param gravatar
     * The gravatar
     */
    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public UserData withGravatar(String gravatar) {
        this.gravatar = gravatar;
        return this;
    }

    /**
     *
     * @return
     * The gravatarUrl
     */
    public String getGravatarUrl() {
        return gravatarUrl;
    }

    /**
     *
     * @param gravatarUrl
     * The gravatarUrl
     */
    public void setGravatarUrl(String gravatarUrl) {
        this.gravatarUrl = gravatarUrl;
    }

    public UserData withGravatarUrl(String gravatarUrl) {
        this.gravatarUrl = gravatarUrl;
        return this;
    }

}