package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcel on 22/11/2016.
 */

public class CadastroEstabelecimentoComercial extends Fragment  {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cadastro_estabelecimentos_comercial, container, false);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_cadastrar_estabelecimento:
                        registerAttemptWithRetrofit(1,
                                "6465432165465",
                                1,
                                1,
                                "Estab Teste",
                                "(19) 9874-9874",
                                "13920-000",
                                "Rua Teste",
                                "Bairro Teste",
                                "22",
                                1,
                                1);


                        fragment = new EstabelecimentosComerciais();

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) view.findViewById(R.id.btn_cadastrar_estabelecimento);
        button.setOnClickListener(listener);

        return view;
    }

    private ApiInterface getInterfaceService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

    private void registerAttemptWithRetrofit(Integer categoria,
                                             String cnpj,
                                             Integer codigoEstabelecimento,
                                             Integer contaBancaria,
                                             String nome,
                                             String telefoneCom,
                                             String cep,
                                             String logradouro,
                                             String bairro,
                                             String numero,
                                             Integer cidade,
                                             Integer estado){

        categoria = 1;
        cnpj = "6465432165465";
        codigoEstabelecimento = null;
        contaBancaria = 18;
        nome = "Estab Teste";
        telefoneCom = "(19) 9874-9874";
        cep = "13920-000";
        logradouro = "Rua Teste";
        bairro = "Bairro Teste";
        numero = "22";
        cidade = 1;
        estado = 1;

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Integer id = null;

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        Endereco endereco = new Endereco(null, bairro, cidade, estado, logradouro, numero, cep);

        List<EstabelecimentoComercial> estabelecimentosList = new ArrayList<EstabelecimentoComercial>();

        EstabelecimentoComercial estabelecimentoComercial = new EstabelecimentoComercial(categoria, cnpj, codigoEstabelecimento, contaBancaria, id, null, nome, telefoneCom, vendedor.getId());
        estabelecimentosList.add(estabelecimentoComercial);

        EstabelecimentoComercial[] estabelecimentosComerciais = new EstabelecimentoComercial[estabelecimentosList.size()];
        estabelecimentosComerciais = estabelecimentosList.toArray(estabelecimentosComerciais);

        vendedor.setEstabelecimentoComercials(estabelecimentosComerciais);

        ApiInterface mApiService = this.getInterfaceService();
        createEnderecoCall(mApiService, estabelecimentoComercial, endereco);

    }

    private void createEnderecoCall(final ApiInterface mApiService, final EstabelecimentoComercial estabelecimentoComercial, Endereco endereco) {
        Call<Endereco> mServiceUsuario = mApiService.createEndereco(endereco);
        mServiceUsuario.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    //Toast.makeText(getActivity(), "Tente novamente. Usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Endereco endereco = response.body();

                    estabelecimentoComercial.setLocalizacao(endereco.getId());
                    createEstabelecimentoComercialCall(mApiService, estabelecimentoComercial);

                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createEstabelecimentoComercialCall(ApiInterface mApiService, EstabelecimentoComercial estabelecimentoComercial) {
        Call<EstabelecimentoComercial> mService = mApiService.estabelecimentoCreate(estabelecimentoComercial);
        mService.enqueue(new Callback<EstabelecimentoComercial>() {
            @Override
            public void onResponse(Call<EstabelecimentoComercial> call, Response<EstabelecimentoComercial> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    EstabelecimentoComercial mEstabelecimentoComercialObject = response.body();
                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
