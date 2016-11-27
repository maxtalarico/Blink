package com.example.marcel.blink_mobile;

import android.content.Context;
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
import com.example.marcel.blink_mobile.models.Aparelho;

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




public class AparelhosListAdapter extends BaseAdapter implements ListAdapter {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";
    private ArrayList<String> list = new ArrayList<String>();
    private AparelhosList context;


    public AparelhosListAdapter(ArrayList<String> list, AparelhosList context) {
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
            view = inflater.inflate(R.layout.aparelhos_list_item, parent, false);


        }

        //Handle TextView and display string from your list
        TextView tvNomeBanco = (TextView) view.findViewById(R.id.campoNomeAparelho);
        tvNomeBanco.setText(list.get(position).split(";")[1]);

        TextView tvNumeroAgencia = (TextView) view.findViewById(R.id.campoSerial);
        tvNumeroAgencia.setText(list.get(position).split(";")[2]);

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button) view.findViewById(R.id.btn_delete);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idAparelho = Integer.parseInt(list.get(position).split(";")[0]);
                boolean result = registerAttemptWithRetrofit(idAparelho, position);
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
        Call<Aparelho> mService = mApiService.deleteAparelho(id);
        mService.enqueue(new Callback<Aparelho>() {
            @Override
            public void onResponse(Call<Aparelho> call, Response<Aparelho> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(context.getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));
                    try {
                        Aparelho aparelho = response.body();
                        result[0] = true;
                        Log.d("Aparelho", "Deletion worked?");
                    } catch (Exception e) {
                        Log.d("Aparelho", "Deletion failed?");
                        result[0] = false;
                    }
                    deleteAparelhoInView(result[0], pos);
                }
            }

            @Override
            public void onFailure(Call<Aparelho> call, Throwable t) {
                call.cancel();
                Toast.makeText(context.getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
                result[0] = false;
            }
        });

        return result[0];
    }

    private void deleteAparelhoInView(boolean result, int position) {
        if (result) {
            list.remove(position); //or some other task
            notifyDataSetChanged();
        } else {
            Toast.makeText(context.getActivity(), "Não foi possível deletar esta conta bancária.", Toast.LENGTH_LONG).show();
        }
    }
}
