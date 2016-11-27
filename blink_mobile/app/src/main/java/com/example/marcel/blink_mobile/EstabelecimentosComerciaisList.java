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
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
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

public class EstabelecimentosComerciaisList extends Fragment implements OnClickListener{
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_estabelecimentos_comerciais_list, container, false);

        // Get the reference of movies

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        registerAttemptWithRetrofit(vendedor.getId());

        vendedor.getEstabelecimentoComercials();

        EstabelecimentoComercial[] estabelecimentoComercials = vendedor.getEstabelecimentoComercials();

        getEstabelecimentos(estabelecimentoComercials, vendedor);

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

    private void registerAttemptWithRetrofit(Integer id){

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));
        ApiInterface mApiService = this.getInterfaceService();
        Call<EstabelecimentoComercial[]> mService = mApiService.getEstabelecimentos(id);
        mService.enqueue(new Callback<EstabelecimentoComercial[]>() {
            @Override
            public void onResponse(Call<EstabelecimentoComercial[]> call, Response<EstabelecimentoComercial[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    EstabelecimentoComercial[] estabelecimentoComercials = response.body();

                    Activity activity = getActivity();
                    Intent i = activity.getIntent();
                    Bundle b = i.getExtras();

                    Usuario usuario = (Usuario) b.getSerializable("Usuario");
                    UserData userData = usuario.getUserData();
                    Vendedor vendedor = userData.getVendedor();

                    vendedor.setEstabelecimentoComercials(estabelecimentoComercials);

                    getEstabelecimentos(estabelecimentoComercials, vendedor);
                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getEstabelecimentos(EstabelecimentoComercial[] estabelecimentoComercials, Vendedor vendedor) {
        View view = getView();

        try {
            int qtdContas = Array.getLength(estabelecimentoComercials);

            Integer idEstabelecimento;
            String nomeEstabelecimento;
            String cnpjEstabelecimento;

            ArrayList<String> estabelecimentosStrings = new ArrayList<String>();

            for (int x = 0; x < qtdContas; x++ ) {
                idEstabelecimento = estabelecimentoComercials[x].getId();
                nomeEstabelecimento = estabelecimentoComercials[x].getNome();
                cnpjEstabelecimento = estabelecimentoComercials[x].getCnpj();

                estabelecimentosStrings.add(Integer.toString(idEstabelecimento) + ";" + nomeEstabelecimento + ";" + cnpjEstabelecimento);
            }

            ListView estabelecimentosList = (ListView) view.findViewById(R.id.estabelecimentos_comerciais_list_listview);
            EstabelecimentosComerciaisListAdapter adapter = new EstabelecimentosComerciaisListAdapter(estabelecimentosStrings, this);
            estabelecimentosList.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Sem Estabelecimentos", "Estabelecimentos null: ");
        }
    }
}