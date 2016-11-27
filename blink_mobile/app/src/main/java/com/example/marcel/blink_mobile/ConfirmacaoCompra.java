package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Autorizacao;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.Compra;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcel on 26/11/2016.
 */

public class ConfirmacaoCompra extends DialogFragment {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_confirmacao_compra, container, false);
        getDialog().setTitle("Simple Dialog");

        Bundle b = getArguments();
        String idCompraString = b.getString("idCompra");
        Integer idCompra = Integer.parseInt(idCompraString);

        getCompra(idCompra);

        return rootView;
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

    public void getCompra(Integer idCompra) {
        ApiInterface mApiService = this.getInterfaceService();
        Call<Compra> mService = mApiService.getCompra(idCompra);
        mService.enqueue(new Callback<Compra>() {
            @Override
            public void onResponse(Call<Compra> call, Response<Compra> response) {
                int statusCode;

                if (!response.isSuccessful()) {
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Compra compra = response.body();

                    generateButtons(compra);

                    TextView tvCompra = (TextView) rootView.findViewById(R.id.compra);
                    tvCompra.setText(compra.toString());

                }
            }

            @Override
            public void onFailure(Call<Compra> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void generateButtons(final Compra compra) {
        final Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Cliente cliente = userData.getCliente();


        final Integer idCompra = compra.getId();
        final Integer idCartao = 1;
        final Integer idCliente = cliente.getId();

        Button confirm = (Button) rootView.findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fecharCompra("Autorizar", idCartao, idCliente, idCompra);
            }
        });

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fecharCompra("Cancelar", idCartao, idCliente, idCompra);
            }
        });

    }

    public void fecharCompra(String status, Integer idCartao, Integer idCliente, Integer idCompra) {
        Autorizacao autorizacao = new Autorizacao(status, idCartao, idCliente, idCompra);

        ApiInterface mApiService = this.getInterfaceService();
        Call<Compra> mService = mApiService.fecharCompra(autorizacao);
        mService.enqueue(new Callback<Compra>() {
            @Override
            public void onResponse(Call<Compra> call, Response<Compra> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Compra compra = response.body();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<Compra> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
