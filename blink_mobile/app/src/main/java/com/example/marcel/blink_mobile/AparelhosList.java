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
import com.example.marcel.blink_mobile.models.Aparelho;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AparelhosList extends Fragment implements OnClickListener{
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_aparelhos_list, container, false);

        // Get the reference of movies

        /*Bundle bArguments = getArguments();
        Integer idEstabelecimento = bArguments.getInt("idEstabelecimento");*/

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Integer idEstabelecimento = b.getInt("idEstabelecimento");

        registerAttemptWithRetrofit(idEstabelecimento);

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

    private void registerAttemptWithRetrofit(Integer idEstabelecimento){

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        ApiInterface mApiService = this.getInterfaceService();
        Call<Aparelho[]> mService = mApiService.getAparelho(idEstabelecimento);
        mService.enqueue(new Callback<Aparelho[]>() {
            @Override
            public void onResponse(Call<Aparelho[]> call, Response<Aparelho[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Aparelho[] aparelhos = response.body();

                    getCartoes(aparelhos);
                }
            }

            @Override
            public void onFailure(Call<Aparelho[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCartoes(Aparelho[] aparelhos) {
        View view = getView();

        try {
            int qtdAparelhos = Array.getLength(aparelhos);

            Log.d("qtdCartoes", Integer.toString(qtdAparelhos));

            String serial;
            String nomeAparelho;
            Integer idAparelho;

            ArrayList<String> aparelhosStrings = new ArrayList<String>();

            for (int x = 0; x < qtdAparelhos; x++ ) {
                idAparelho = aparelhos[x].getId();
                nomeAparelho = aparelhos[x].getNome();
                serial = aparelhos[x].getSerial();

                Log.d("Cartao: ", aparelhos[x].toString());

                aparelhosStrings.add(Integer.toString(idAparelho) + ";" + nomeAparelho + ";" + serial);
            }

            ListView cartoesList = (ListView) view.findViewById(R.id.aparelhos_list_listview);
            AparelhosListAdapter adapter = new AparelhosListAdapter(aparelhosStrings, this);
            cartoesList.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Sem Cartões", "Cartões null: ");
        }
    }
}