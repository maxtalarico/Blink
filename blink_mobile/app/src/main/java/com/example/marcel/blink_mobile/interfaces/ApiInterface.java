package com.example.marcel.blink_mobile.interfaces;

/**
 * Created by Marcel on 03/10/2016.
 */

import com.example.marcel.blink_mobile.models.Aparelho;
import com.example.marcel.blink_mobile.models.Autorizacao;
import com.example.marcel.blink_mobile.models.Cartao;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.Compra;
import com.example.marcel.blink_mobile.models.Compras;
import com.example.marcel.blink_mobile.models.ContaBancaria;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercialEndereco;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Venda;
import com.example.marcel.blink_mobile.models.Vendedor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/authenticate")
    Call<Usuario> authenticate(@Field("email") String email,
                               @Field("senha") String senha);

    @POST("/aparelho?populate=0")
    Call<Aparelho> aparelhoCreate(@Body Aparelho aparelho);

    @GET("/aparelho?populate=0")
    Call<Aparelho[]> getAparelho(@Query("proprietario") Integer id);

    @DELETE("/aparelho/{id}?populate=0")
    Call<Aparelho> deleteAparelho(@Path("id") Integer id);

    @POST("/cartao")
    Call<Cartao> cartaoCreate(@Body Cartao cartao);

    @GET("/cartao")
    Call<Cartao[]> getCartao(@Query("proprietario") Integer id);

    @DELETE("/cartao/{id}")
    Call<Cartao> deleteCartao(@Path("id") Integer id);

    @POST("/contabancaria")
    Call<ContaBancaria> contaBancariaCreate(@Body ContaBancaria contaBancaria);

    @GET("/contabancaria")
    Call<ContaBancaria[]> getContas(@Query("titular") Integer id);

    @DELETE("/contabancaria/{id}")
    Call<ContaBancaria> deleteConta(@Path("id") Integer id);

    @POST("/estabelecimentocomercial")
    Call<EstabelecimentoComercial> estabelecimentoCreate(@Body EstabelecimentoComercial estabelecimentoComercial);

    @GET("/estabelecimentocomercial")
    Call<EstabelecimentoComercial[]> getEstabelecimentos(@Query("dono") Integer id);

    @GET("/estabelecimentocomercial")
    Call<EstabelecimentoComercial> getEstabelecimentoComercial(@Query("id") Integer id);

    @DELETE("/estabelecimentocomercial/{id}")
    Call<EstabelecimentoComercial> deleteEstabelecimentos(@Path("id") Integer id);

    @PUT("/estabelecimentocomercial/{id}?populate=0")
    Call<EstabelecimentoComercial> estabelecimentoUpdate(@Path("id") Integer id, @Body EstabelecimentoComercial estabelecimentoComercial);

    @POST("/usuario")
    Call<Usuario> userCreate(@Body UserData usuario);

    @PUT("/usuario/{id}")
    Call<UserData> userUpdate(@Path("id") Integer id, @Body UserData userData);

    @PUT("/vendedor/{id}")
    Call<Vendedor> vendedorUpdate(@Path("id") Integer id, @Body Vendedor vendedor);

    @PUT("/cliente/{id}")
    Call<Cliente> clienteUpdate(@Path("id") Integer id, @Body Cliente cliente);

    @POST("/endereco")
    Call<Endereco> createEndereco(@Body Endereco endereco);

    @PUT("/endereco/{id}")
    Call<Endereco> enderecoUpdate(@Path("id") Integer id, @Body Endereco endereco);

    @POST("/venda")
    Call<Compra> compraCreate(@Body Venda venda);

    @GET("/compra?populate=0")
    Call<Compra> getCompra(@Query("id") Integer id);

    @POST("/autorizar")
    Call<Compra> fecharCompra(@Body Autorizacao autorizacao);

    @GET("/compra")
    Call<Compras[]> getComprasHistory(@Query("cliente") Integer idCliente, @Query("status") String status);

    @GET("/compra")
    Call<Compras> getCompras(@Query("id") Integer idCompra);

    @GET("/compra")
    Call<Compras[]> getComprasHistoryVendedor(@Query("estabelecimento") Integer idEstabelecimento, @Query("status") String status);

    @GET("/estabelecimentocomercial/{id}")
    Call<EstabelecimentoComercialEndereco> getEstabelecimentoComercialEndereco(@Path("id") Integer id);
}
