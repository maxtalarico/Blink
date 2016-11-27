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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.ContaBancaria;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContasBancariasList extends Fragment implements OnClickListener{
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contas_bancarias_list, container, false);

        // Get the reference of movies

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        registerAttemptWithRetrofit(vendedor.getId(), vendedor);

        ContaBancaria[] contas = vendedor.getContasBancarias();

        getContas(contas, vendedor);

        return view;
    }

    public void onClick(View v) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.btnContinuar:
                break;

            default:
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
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

    private void registerAttemptWithRetrofit(Integer id, Vendedor vendedor){

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));
        ApiInterface mApiService = this.getInterfaceService();
        Call<ContaBancaria[]> mService = mApiService.getContas(id);
        mService.enqueue(new Callback<ContaBancaria[]>() {
            @Override
            public void onResponse(Call<ContaBancaria[]> call, Response<ContaBancaria[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    ContaBancaria[] contas = response.body();

                    Activity activity = getActivity();
                    Intent i = activity.getIntent();
                    Bundle b = i.getExtras();

                    Usuario usuario = (Usuario) b.getSerializable("Usuario");
                    UserData userData = usuario.getUserData();
                    Vendedor vendedor = userData.getVendedor();

                    vendedor.setContasBancarias(contas);

                    getContas(contas, vendedor);
                }
            }

            @Override
            public void onFailure(Call<ContaBancaria[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getContas(ContaBancaria[] contas, Vendedor vendedor) {
        View view = getView();

        try {
            int qtdContas = Array.getLength(contas);

            String nomeBanco;
            String numeroAgencia;
            Integer idConta;

            ArrayList<String> contasStrings = new ArrayList<String>();

            for (int x = 0; x < qtdContas; x++ ) {
                idConta = contas[x].getId();
                nomeBanco = vendedor.getNome() + contas[x].getBanco();
                numeroAgencia = contas[x].getNumeroConta()+ contas[x].getDigitoConta() + contas[x].getAgencia() + contas[x].getDigitoAgencia();

                contasStrings.add(Integer.toString(idConta) + ";" + nomeBanco + ";" + numeroAgencia);
            }

            ListView contasList = (ListView) view.findViewById(R.id.contas_bancarias_list_listview);
            ContasBancariasListAdapter adapter = new ContasBancariasListAdapter(contasStrings, this);
            contasList.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Sem Contas", "Contas null: ");
        }
    }
}