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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Compras;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryVendedor extends Fragment implements View.OnClickListener {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    Spinner estabelecimentoSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history_vendedor_list, container, false);

        // Get the reference of movies
        ListView historyList = (ListView) view.findViewById(R.id.history_vendedor_home_listview);

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();
        Compras[] compras = (Compras[]) b.getSerializable("Compras");
        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        setEstabelecimentosSpinner(vendedor.getId());

        registerAttemptWithRetrofit();

        getCompras(compras);

        /*String[] history = {"History Test 1",
                "History Test 2",
                "History Test 3",
                "History Test 4",
                "History Test 5",
                "History Test 6",
                "History Test 7",
                "History Test 8",
                "History Test 9",
                "History Test 10",
                "History Test 11",
                "History Test 12",
                "History Test 13",
                "History Test 14",
                "History Test 15",
                "History Test 16",
                "History Test 17",
                "History Test 18",
                "History Test 19",
                "History Test 20"};

        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, history);
        // Set The Adapter
        historyList.setAdapter(arrayAdapter);*/

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

    private void registerAttemptWithRetrofit(){
        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        final Integer idEstabelecimentoComercial = b.getInt("idEstabelecimentoComercial");

        ApiInterface mApiService = this.getInterfaceService();
        Call<Compras[]> mService = mApiService.getComprasHistoryVendedor(idEstabelecimentoComercial);
        mService.enqueue(new Callback<Compras[]>() {
            @Override
            public void onResponse(Call<Compras[]> call, Response<Compras[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Compras[] compras = response.body();

                    Activity activity = getActivity();
                    Intent i = activity.getIntent();
                    Bundle b = i.getExtras();
                    i.putExtra("Compras", compras);

                    getCompras(compras);
                }
            }

            @Override
            public void onFailure(Call<Compras[]> call, Throwable t) {
                call.cancel();
                Log.d("Error", "onFailure: " + t);
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCompras(Compras[] compras) {
        View view = getView();

        try {
            int qtdCompras = Array.getLength(compras);

            Log.d("qtdCompras", Integer.toString(qtdCompras));

            String estabelecimento;
            String data;
            String valor;
            Integer idCompras;

            String compraString;

            ArrayList<String> comprasStrings = new ArrayList<String>();



            for (int x = 0; x < qtdCompras; x++ ) {

                Date date = compras[x].getData(); // your date
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);

                idCompras = compras[x].getId();
                estabelecimento = compras[x].getEstabelecimento().getNome();
                data = Integer.toString(day) + "/" +
                        Integer.toString(month) + "/" +
                        Integer.toString(year);
                //valor = Float.toString(compras[x].getValorTotal());
                valor = String.format("%.2f", compras[x].getValorTotal());

                compraString = Integer.toString(idCompras) + ";" + estabelecimento + ";" + data + ";R$ " + valor;

                Log.d("Compra: ", compraString);

                comprasStrings.add(compraString);
            }

            ListView comprasList = (ListView) view.findViewById(R.id.history_vendedor_home_listview);
            HistoryVendedorListAdapter adapter = new HistoryVendedorListAdapter(comprasStrings, this);
            comprasList.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Sem Cartões", e.toString());
        }
    }

    public void setEstabelecimentosSpinner(Integer idVendedor) {
        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));
        ApiInterface mApiService = this.getInterfaceService();
        Call<EstabelecimentoComercial[]> mService = mApiService.getEstabelecimentos(idVendedor);
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

                    getEstabelecimentosArrayList(estabelecimentoComercials, vendedor);
                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getEstabelecimentosArrayList(EstabelecimentoComercial[] estabelecimentoComercials, Vendedor vendedor) {
        View view = getView();

        Log.d("Sem Estabelecimentos", "here");

        try {
            int qtdContas = Array.getLength(estabelecimentoComercials);

            Integer idEstabelecimento;
            String nomeEstabelecimento;

            ArrayList<String> estabelecimentosStrings = new ArrayList<String>();

            for (int x = 0; x < qtdContas; x++ ) {
                idEstabelecimento = estabelecimentoComercials[x].getId();
                nomeEstabelecimento = estabelecimentoComercials[x].getNome();

                estabelecimentosStrings.add(Integer.toString(idEstabelecimento) + " - " + nomeEstabelecimento);
            }

            Activity activity = getActivity();
            Intent i = activity.getIntent();

            estabelecimentoSpinner = (Spinner) view.findViewById(R.id.spinnerEstabelecimento);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, estabelecimentosStrings);
            estabelecimentoSpinner.setAdapter(adapter);

            String estabelecimentoSpinnerString = estabelecimentoSpinner.getSelectedItem().toString();
            Integer idEstabelecimentoComercial = Integer.parseInt(estabelecimentoSpinnerString.split(" - ")[0]);
            i.putExtra("idEstabelecimentoComercial", idEstabelecimentoComercial);
            registerAttemptWithRetrofit();

            estabelecimentoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Activity activity = getActivity();
                    Intent i = activity.getIntent();

                    String estabelecimentoSpinnerString = estabelecimentoSpinner.getSelectedItem().toString();
                    Integer idEstabelecimentoComercial = Integer.parseInt(estabelecimentoSpinnerString.split(" - ")[0]);
                    i.putExtra("idEstabelecimentoComercial", idEstabelecimentoComercial);
                    Log.d("id", "onItemSelected: " + estabelecimentoSpinnerString.split(" - ")[0]);
                    registerAttemptWithRetrofit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        } catch (Exception e) {
            Log.d("Sem Estabelecimentos", e.toString());
        }
    }
}