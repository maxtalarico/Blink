package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Cartao;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
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

    private View rootView;

    EditText nome;
    String txt_nome_cartao;
    EditText numero;
    String txt_numero_cartao;
    EditText data;
    String txt_data_validade;
    EditText codigo;
    String txt_codigo_seguranca;
    /*EditText proprietario;
    String txt_proprietario;*/
    Spinner spinnerBandeiras;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_cadastro_cartoes, container, false);

        rootActivity = getActivity();

        EditText nome = (EditText) rootView.findViewById(R.id.txt_nome_cartao);
        String txt_nome_cartao = nome.getText().toString();
        EditText numero = (EditText) rootView.findViewById(R.id.txt_numero_cartao);
        String txt_numero_cartao = numero.getText().toString();
        EditText data = (EditText) rootView.findViewById(R.id.txt_data_validade);
        String txt_data_validade = data.getText().toString();
        EditText codigo = (EditText) rootView.findViewById(R.id.txt_codigo_seguranca);
        String txt_codigo_seguranca = codigo.getText().toString();
        /*EditText proprietario = (EditText) rootView.findViewById(R.id.txt_proprietario);
        String txt_proprietario = proprietario.getText().toString();*/

        ArrayList<String> bandeiras = getBandeiras("bandeiras.json");
        spinnerBandeiras=(Spinner) rootView.findViewById(R.id.spn_bandeira);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(rootView.getContext(), R.layout.spinner_layout, R.id.textView, bandeiras);
        spinnerBandeiras.setAdapter(adapter3);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



                switch (v.getId()) {
                    case R.id.btn_cadastrar_cartao:

                        EditText nome = (EditText) rootView.findViewById(R.id.txt_nome_cartao);
                        String txt_nome_cartao = nome.getText().toString();
                        EditText numero = (EditText) rootView.findViewById(R.id.txt_numero_cartao);
                        String txt_numero_cartao = numero.getText().toString();
                        EditText data = (EditText) rootView.findViewById(R.id.txt_data_validade);
                        String txt_data_validade = data.getText().toString();
                        EditText codigo = (EditText) rootView.findViewById(R.id.txt_codigo_seguranca);
                        String txt_codigo_seguranca = codigo.getText().toString();
                        spinnerBandeiras=(Spinner) rootView.findViewById(R.id.spn_bandeira);
                        String txt_bandeira = spinnerBandeiras.getSelectedItem().toString();
                       /* EditText proprietario = (EditText) rootView.findViewById(R.id.txt_proprietario);
                        String txt_proprietario = proprietario.getText().toString();*/


                        if ("".equals(nome.getText().toString().trim())) {
                            nome.setError("Campo Obrigatório");
                        } if ("".equals(numero.getText().toString().trim())) {
                        numero.setError("Campo obrigatório");
                    } if ("".equals(data.getText().toString().trim())) {
                        data.setError("Campo obrigatório");
                    } if ("".equals(codigo.getText().toString().trim())) {
                        codigo.setError("Campo obrigatório");
                    }else {
                        registerAttemptWithRetrofit(txt_nome_cartao,
                                txt_numero_cartao,
                                txt_data_validade,
                                Integer.parseInt(txt_codigo_seguranca),
                                txt_bandeira);

                        fragment = new Cartoes();

                    }
                        break;

                    case R.id.btn_cancelar:
                        fragment = new Cartoes();
                        //fragment = new EstabelecimentosComerciais();
                        break;

                    default:
                        break;
                }

/*
                        registerAttemptWithRetrofit(txt_nome_cartao,
                                txt_numero_cartao,
                                txt_data_validade,
                                Integer.parseInt(txt_codigo_seguranca),
                                txt_bandeira);

                        fragment = new Cartoes();

                case R.id.btn_cancelar:
                fragment = new EstabelecimentosComerciaisList();
                //fragment = new EstabelecimentosComerciais();
                break;
                    default:
                        break;
                }
*/

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) rootView.findViewById(R.id.btn_cadastrar_cartao);
        button.setOnClickListener(listener);

        return rootView;
    }

    public void fValidar(View view) {
        EditText nome = (EditText) rootView.findViewById(R.id.txt_nome_cartao);
        String txt_nome_cartao = nome.getText().toString();
        EditText numero = (EditText) rootView.findViewById(R.id.txt_numero_cartao);
        String txt_numero_cartao = numero.getText().toString();
        EditText data = (EditText) rootView.findViewById(R.id.txt_data_validade);
        String txt_data_validade = data.getText().toString();
        EditText codigo = (EditText) rootView.findViewById(R.id.txt_codigo_seguranca);
        String txt_codigo_seguranca = codigo.getText().toString();
        /*EditText proprietario = (EditText) rootView.findViewById(R.id.txt_proprietario);
        String txt_proprietario = proprietario.getText().toString();*/


        switch (view.getId()) {
            case R.id.btn_gerar:
                if (txt_nome_cartao == null || txt_nome_cartao.equals("")) {
                    nome.setError("Campo Obrigatório");
                } if (txt_numero_cartao == null || txt_numero_cartao.equals("")) {
                    numero.setError("Campo obrigatório");
                 }if (txt_data_validade == null || txt_data_validade.equals("")) {
                    data.setError("Campo obrigatório");
            }if (txt_codigo_seguranca == null || txt_codigo_seguranca.equals("")) {
                codigo.setError("Campo obrigatório");
            /*}if (txt_proprietario == null || txt_proprietario.equals("")) {
                 proprietario.setError("Campo obrigatório");*/
            }
                break;

        }

        EditText numeroCartao = (EditText) view.findViewById(R.id.txt_numero_cartao);
        MaskEditTextChangedListener maskNum = new MaskEditTextChangedListener("#### #### #### ####", numeroCartao);
        EditText dataV = (EditText) view.findViewById(R.id.txt_data_validade);
        MaskEditTextChangedListener maskdataV = new MaskEditTextChangedListener("##/##", dataV);
        EditText codigoS = (EditText) view.findViewById(R.id.txt_codigo_seguranca);
        MaskEditTextChangedListener maskCod = new MaskEditTextChangedListener("###", codigoS);

        numeroCartao.addTextChangedListener((TextWatcher) maskNum);
        dataV.addTextChangedListener((TextWatcher) maskdataV);
        codigoS.addTextChangedListener((TextWatcher) maskCod);

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
                                              String numero,
                                              String dataVencimento,
                                              int codigoSeguranca,
                                              String bandeira){

        /*nome = "Cliente Teste";
        numero = "1111112332";
        senha = "123456";
        dataVencimento = "11/2020";
        codigoSeguranca = 777;
        bandeira = "Master Card";*/

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Integer id = null;

        final Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Cliente cliente = userData.getCliente();

        List<Cartao> cartoesList = new ArrayList<Cartao>();
        Cartao cartao = new Cartao(bandeira, codigoSeguranca, dataVencimento, id, nome, numero, cliente.getId()/*, cliente*/);
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

    public ArrayList<String> getBandeiras(String jsonFile) {
        JSONArray jsonBandeiras = null;
        ArrayList<String> bandeiraList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(jsonFile);
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");

            JSONObject object = new JSONObject(json);
            jsonBandeiras  = object.getJSONArray("bandeiras");

            for (int i = 0; i < jsonBandeiras.length(); i++)
            {
                bandeiraList.add(jsonBandeiras.getString(i));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return bandeiraList;
    }
}
