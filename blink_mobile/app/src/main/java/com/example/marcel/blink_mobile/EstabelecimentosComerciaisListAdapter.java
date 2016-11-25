package com.example.marcel.blink_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Marcel on 23/11/2016.
 */




public class EstabelecimentosComerciaisListAdapter extends BaseAdapter implements ListAdapter {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";
    private ArrayList<String> list = new ArrayList<String>();
    private EstabelecimentosComerciaisList context;


    public EstabelecimentosComerciaisListAdapter(ArrayList<String> list, EstabelecimentosComerciaisList context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        long r = 0;
        return r;
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.estabelecimentos_comerciais_list_item, parent, false);


        }

        //Handle TextView and display string from your list
        TextView tvNomeEstabelecimento = (TextView)view.findViewById(R.id.nomeEstabelecimento);
        tvNomeEstabelecimento.setText(list.get(position).split(";")[1]);

        TextView tvCnpjEstabelecimento = (TextView)view.findViewById(R.id.cnpjEstabelecimento);
        tvCnpjEstabelecimento.setText(list.get(position).split(";")[2]);

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.btn_delete);
        Button editBtn = (Button)view.findViewById(R.id.btn_edit);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer idEstabelecimento = Integer.parseInt(list.get(position).split(";")[0]);
                boolean result = registerAttemptWithRetrofit(idEstabelecimento, position);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idEstabelecimento = Integer.parseInt(list.get(position).split(";")[0]);

                Fragment fragment = null;
                FragmentManager fragmentManager = context.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment = new EdicaoEstabelecimentoComercial();

                Bundle b = new Bundle();
                b.putInt("idEstabelecimento", idEstabelecimento);

                fragment.setArguments(b);

                if (fragment != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            }
        });

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

    private boolean registerAttemptWithRetrofit(Integer id, int position){
        final boolean[] result = {false};
        final int pos = position;

        ApiInterface mApiService = this.getInterfaceService();
        Call<EstabelecimentoComercial> mService = mApiService.deleteEstabelecimentos(id);
        mService.enqueue(new Callback<EstabelecimentoComercial>() {
            @Override
            public void onResponse(Call<EstabelecimentoComercial> call, Response<EstabelecimentoComercial> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(context.getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));
                    try {
                        EstabelecimentoComercial estabelecimentoComercial = response.body();
                        result[0] = true;
                        Log.d("Estabelecimento", "Deletion worked?");
                    } catch (Exception e) {
                        Log.d("Estabelecimento", "Deletion failed?");
                        result[0] = false;
                    }
                    deleteEstabelecimentoInView(result[0], pos);
                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial> call, Throwable t) {
                call.cancel();
                Toast.makeText(context.getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
                result[0] = false;
            }
        });

        return result[0];
    }

    private void deleteEstabelecimentoInView(boolean result, int position) {
        if (result) {
            list.remove(position); //or some other task
            notifyDataSetChanged();
        } else {
            Toast.makeText(context.getActivity(), "Não foi possível deletar este estabelecimento comercial.", Toast.LENGTH_LONG).show();
        }
    }
}

