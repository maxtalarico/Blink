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
import com.example.marcel.blink_mobile.models.Cartao;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class CadastroCartao extends Fragment  {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    Activity rootActivity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cadastro_cartoes, container, false);

        rootActivity = getActivity();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_cadastrar_cartao:
                        registerAttemptWithRetrofit("Cliente Teste",
                                                    1111111111,
                                                    "123456",
                                                    "12/11/2020",
                                                    777,
                                                    "Master Card");

                        fragment = new Cartoes();

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) view.findViewById(R.id.btn_cadastrar_cartao);
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

    private void registerAttemptWithRetrofit( String nome,
                                              int numero,
                                              String senha,
                                              String dataVencimento,
                                              int codigoSeguranca,
                                              String bandeira){

        nome = "Cliente Teste";
        numero = 1111112332;
        senha = "123456";
        dataVencimento = "12/11/2020";
        codigoSeguranca = 777;
        bandeira = "Master Card";

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = format.parse(dataVencimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Integer id = null;

        final Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Cliente cliente = userData.getCliente();

        List<Cartao> cartoesList = new ArrayList<Cartao>();
        Cartao cartao = new Cartao(bandeira, codigoSeguranca, data, id, nome, numero, cliente.getId()/*, cliente*/);
        cartoesList.add(cartao);

        Cartao[] cartoes = new Cartao[cartoesList.size()];
        cartoes = cartoesList.toArray(cartoes);

        cliente.setCartoes(cartoes);

        Log.d("UsuarioCliente", userData.toString());

        ApiInterface mApiService = this.getInterfaceService();
        Call<Cartao> mService = mApiService.cartaoCreate(cartao);
        mService.enqueue(new Callback<Cartao>() {
            @Override
            public void onResponse(Call<Cartao> call, Response<Cartao> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(activity, "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Cartao mCartaoObject = response.body();


                }
            }

            @Override
            public void onFailure(Call<Cartao> call, Throwable t) {
                call.cancel();
                Toast.makeText(activity, "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
