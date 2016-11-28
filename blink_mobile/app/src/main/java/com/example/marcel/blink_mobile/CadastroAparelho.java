package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Aparelho;

import java.util.ArrayList;
import java.util.Date;
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

public class CadastroAparelho extends Fragment  {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    Activity rootActivity;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cadastro_aparelhos, container, false);

        rootActivity = getActivity();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_cadastrar_aparelho:
                        EditText dispositivo = (EditText) getView().findViewById(R.id.txt_nome_dispositivo);
                        //EditText dt_ativacao = (EditText) getView().findViewById(R.id.txt_data_ativacao);
                        //EditText dt_u_uso = (EditText) getView().findViewById(R.id.txt_data_ultimo_uso);
                        //EditText serial = (EditText) getView().findViewById(R.id.txt_serial);
                        //EditText proprietario = (EditText) getView().findViewById(R.id.txt_proprietario);

                        if ("".equals(dispositivo.getText().toString().trim())) {
                            dispositivo.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Nome do Dispositivo é Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        /*if ("".equals(dt_ativacao.getText().toString().trim())) {
                            dt_ativacao.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Agência Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ("".equals(dt_u_uso.getText().toString().trim())) {
                            dt_u_uso.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }if ("".equals(serial.getText().toString().trim())) {
                        serial.setError("Campo obrigatório");
                        //Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                        return;
                        }if ("".equals(proprietario.getText().toString().trim())) {
                            proprietario.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }*/ else {
                            registerAttemptWithRetrofit(dispositivo.getText().toString());

                            fragment = new Aparelhos();
                        }

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) view.findViewById(R.id.btn_cadastrar_aparelho);
        button.setOnClickListener(listener);

        /*EditText dtAtv = (EditText) view.findViewById(R.id.txt_data_ativacao);
        MaskEditTextChangedListener maskdtAtv = new MaskEditTextChangedListener("##/##/####", dtAtv);
        EditText datUU = (EditText) view.findViewById(R.id.txt_data_ultimo_uso);
        MaskEditTextChangedListener maskdatUU = new MaskEditTextChangedListener("##/##/####", datUU);

        dtAtv.addTextChangedListener((TextWatcher) maskdtAtv);
        datUU.addTextChangedListener((TextWatcher) maskdatUU);*/

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

    private void registerAttemptWithRetrofit(String nome){

        Date dataAtivacao = new Date();
        String serial = Build.SERIAL;
        String status = "Ativo";
        Date ultimoUso = new Date();

        final Activity activity = getActivity();
        Bundle b = getArguments();
        Integer idEstabelecimento = b.getInt("idEstabelecimento");


        List<Aparelho> aparelhosList = new ArrayList<Aparelho>();
        Aparelho aparelho = new Aparelho(dataAtivacao, null, nome, serial, status, ultimoUso, idEstabelecimento);

        Log.d("Aparelho", aparelho.toString());

        aparelhosList.add(aparelho);

        Aparelho[] aparelhos = new Aparelho[aparelhosList.size()];
        aparelhos = aparelhosList.toArray(aparelhos);

        ApiInterface mApiService = this.getInterfaceService();
        Call<Aparelho> mService = mApiService.aparelhoCreate(aparelho);
        mService.enqueue(new Callback<Aparelho>() {
            @Override
            public void onResponse(Call<Aparelho> call, Response<Aparelho> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(activity, "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Aparelho aparelho = response.body();


                }
            }

            @Override
            public void onFailure(Call<Aparelho> call, Throwable t) {
                call.cancel();
                Toast.makeText(activity, "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
