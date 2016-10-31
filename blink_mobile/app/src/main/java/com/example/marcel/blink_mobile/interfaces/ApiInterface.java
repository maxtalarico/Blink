package com.example.marcel.blink_mobile.interfaces;

/**
 * Created by Marcel on 03/10/2016.
 */

import com.example.marcel.blink_mobile.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("authenticate")
    Call<Usuario> authenticate(@Field("email") String email,
                               @Field("senha") String senha);

    @FormUrlEncoded
    @POST("/usuario/create")
    Call<Usuario> userCreate(@Field("email") String email,
                             @Field("senha") String senha,
                             @Field("nome") String nome,
                             @Field("dataNascimento") String dataNascimento,
                             @Field("cpf") String cpf,
                             @Field("telefoneRes") String telefoneRes,
                             @Field("celular") String celular,
                             @Field("telefoneCom") String telefoneCom,
                             @Field("cep") String cep,
                             @Field("cmb_estado") String estado,
                             @Field("cmb_cidade") String cidade,
                             @Field("bairro") String bairro,
                             @Field("logradouro") String logradouro,
                             @Field("logradouro") String numero);

    @FormUrlEncoded
    @POST("/usuario/update")
    Call<Usuario> userUpdate(@Field("nome") String nome,
                             @Field("dataNascimento") String dataNascimento,
                             @Field("cpf") String cpf,
                             @Field("telefoneRes") String telefoneRes,
                             @Field("celular") String celular,
                             @Field("telefoneCom") String telefoneCom,
                             @Field("cep") String cep,
                             @Field("cmb_estado") String estado,
                             @Field("cmb_cidade") String cidade,
                             @Field("bairro") String bairro,
                             @Field("logradouro") String logradouro);

    @FormUrlEncoded
    @POST("/estabelecimentocomercial/create")
    Call<Usuario> estabelecimentoCreate(@Field("nome") String nome,
                                        @Field("cnpj") String cnpj,
                                        @Field("telefoneCom") String telefoneCom,
                                        @Field("cmb_categoria") String categoria,
                                        @Field("cep") String cep,
                                        @Field("cmb_estado") String estado,
                                        @Field("cmb_cidade") String cidade,
                                        @Field("bairro") String bairro,
                                        @Field("logradouro") String logradouro,
                                        @Field("cmb_conta_bancaria") String contaBancaria);

    @FormUrlEncoded
    @POST("/estabelecimentocomercial/update")
    Call<Usuario> estabelecimentoUpdate(@Field("nome") String nome,
                                        @Field("cnpj") String cnpj,
                                        @Field("telefoneCom") String telefoneCom,
                                        @Field("cmb_categoria") String categoria,
                                        @Field("cep") String cep,
                                        @Field("cmb_estado") String estado,
                                        @Field("cmb_cidade") String cidade,
                                        @Field("bairro") String bairro,
                                        @Field("logradouro") String logradouro,
                                        @Field("cmb_conta_bancaria") String contaBancaria);


    @FormUrlEncoded
    @POST("/contabancaria/create")
    Call<Usuario> contabancariaCreate(@Field("cmb_banco") String banco,
                                      @Field("cmb_tipoConta") String tipoConta,
                                      @Field("agencia") String agencia,
                                      @Field("digitoAgencia") String digitoAgencia,
                                      @Field("conta") String conta,
                                      @Field("digitoConta") String digitoConta);

    @FormUrlEncoded
    @POST("/contabancaria/update")
    Call<Usuario> contabancariaUpdate(@Field("cmb_banco") String banco,
                                      @Field("cmb_tipoConta") String tipoConta,
                                      @Field("agencia") String agencia,
                                      @Field("digitoAgencia") String digitoAgencia,
                                      @Field("conta") String conta,
                                      @Field("digitoConta") String digitoConta);



}
