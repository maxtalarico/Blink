package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.ContaBancaria;
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

public class CadastroContasBancarias extends Fragment  {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cadastro_contas_bancarias, container, false);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_cadastrar_conta:
                        registerAttemptWithRetrofit(1,
                                                    "3948",
                                                    "0",
                                                    "01089842",
                                                    "3",
                                                    1);

                        fragment = new ContasBancarias();

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) view.findViewById(R.id.btn_cadastrar_conta);
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

    private void registerAttemptWithRetrofit( Integer banco,
                                              String agencia,
                                              String digitoAgencia,
                                              String numeroConta,
                                              String digitoConta,
                                              Integer tipoConta){

        banco = 1;
        agencia = "3948";
        digitoAgencia = "0";
        numeroConta = "01089847";
        digitoConta = "3";
        tipoConta = 1;

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Integer id = null;

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        List<ContaBancaria> contasList = new ArrayList<ContaBancaria>();
        ContaBancaria conta = new ContaBancaria(agencia, banco, digitoAgencia, digitoConta, id, numeroConta, tipoConta, vendedor.getId());
        contasList.add(conta);

        ContaBancaria[] contas = new ContaBancaria[contasList.size()];
        contas = contasList.toArray(contas);

        vendedor.setContasBancarias(contas);

        Log.d("UsuarioCliente", userData.toString());

        ApiInterface mApiService = this.getInterfaceService();
        Call<ContaBancaria> mService = mApiService.contaBancariaCreate(conta);
        mService.enqueue(new Callback<ContaBancaria>() {
            @Override
            public void onResponse(Call<ContaBancaria> call, Response<ContaBancaria> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    ContaBancaria mContaBancariaObject = response.body();


                }
            }

            @Override
            public void onFailure(Call<ContaBancaria> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
