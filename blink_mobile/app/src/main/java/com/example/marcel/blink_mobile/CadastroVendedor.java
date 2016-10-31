package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tayna on 03/10/2016.
 */

public class CadastroVendedor extends ActionBarActivity  {
//implements View.OnClickListener
    final String REGISTER_URL = "http://blink-brunopansani-1.c9users.io/";

    private EditText emailEdTex;
    private EditText emailConfEdTex;

    private EditText senhaEdTex;
    private EditText senhaConfEdTex;

    private EditText nomeEdTex;
    private EditText dataNascimentoEdTex;
    private EditText cpfEdTex;
    private EditText telResEdTex;
    private EditText telComEdTex;
    private EditText telCom2EdTex;

    private EditText cepEdTex;
    private Spinner estadoSpinner;
    private Spinner cidadeSpinner;
    private EditText logradouroEdTex;
    private EditText bairroEdTex;
    private EditText numeroEdTex;

    private String email;
    private String emailConf;

    private String senha;
    private String senhaConf;

    private String nome;
    private String dataNascimento;
    private String cpf;
    private String telRes;
    private String telCom;
    private String telCom2;

    private String cep;
    private String estado;
    private String cidade;
    private String logradouro;
    private String bairro;
    private String numero;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cadastrar:
                    cadastrarVendedor();
                    break;
                case R.id.btn_cancelar:
                    cancelar();
                    break;
                default:
                    break;
            }
        }
    };

    protected void cadastrarVendedor() {
        emailEdTex = (EditText) findViewById(R.id.txt_email);
        emailConfEdTex = (EditText) findViewById(R.id.txt_confirmar_email);

        senhaEdTex = (EditText) findViewById(R.id.txt_senha);
        senhaConfEdTex = (EditText) findViewById(R.id.txt_confirmar_senha);

        nomeEdTex = (EditText) findViewById(R.id.txt_nome);
        dataNascimentoEdTex = (EditText) findViewById(R.id.txt_data_nascimento);
        cpfEdTex = (EditText) findViewById(R.id.txt_cpf);
        telResEdTex = (EditText) findViewById(R.id.txt_telefone_residencial);
        telComEdTex = (EditText) findViewById(R.id.txt_teefone_comercial);
        telCom2EdTex = (EditText) findViewById(R.id.txt_telefone_celular);

        cepEdTex = (EditText) findViewById(R.id.txt_cep);
        estadoSpinner = (Spinner) findViewById(R.id.spn_estado);
        cidadeSpinner = (Spinner) findViewById(R.id.spn_cidade);
        logradouroEdTex = (EditText) findViewById(R.id.txt_endereco);
        bairroEdTex = (EditText) findViewById(R.id.txt_bairro);
        numeroEdTex = (EditText) findViewById(R.id.txt_numero);

        email = emailEdTex.getText().toString().trim();
        emailConf = emailConfEdTex.getText().toString().trim();

        senha = senhaEdTex.getText().toString().trim();;
        senhaConf = senhaConfEdTex.getText().toString().trim();;

        nome = nomeEdTex.getText().toString().trim();;
        dataNascimento = dataNascimentoEdTex.getText().toString().trim();;
        cpf = cpfEdTex.getText().toString().trim();;
        telRes = telResEdTex.getText().toString().trim();;
        telCom = telComEdTex.getText().toString().trim();;
        telCom2 = telCom2EdTex.getText().toString().trim();;

        cep = cepEdTex.getText().toString().trim();;
        estado = estadoSpinner.getSelectedItem().toString().trim();;
        cidade = cidadeSpinner.getSelectedItem().toString().trim();;
        logradouro = logradouroEdTex.getText().toString().trim();;
        bairro = bairroEdTex.getText().toString().trim();;
        numero = numeroEdTex.getText().toString().trim();;

        registerAttemptWithRetrofit(email,
                                    senha,
                                    nome,
                                    dataNascimento,
                                    cpf,
                                    telRes,
                                    telCom,
                                    telCom2,
                                    cep,
                                    estado,
                                    cidade,
                                    logradouro,
                                    bairro,
                                    numero);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vendedor);

        ArrayList<String> items = getEstados("estados-cidades.json");
        final Spinner spinner=(Spinner) this.findViewById(R.id.spn_estado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, items);
        spinner.setAdapter(adapter);

        final Activity curView = this;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estado = spinner.getSelectedItem().toString();;

                ArrayList<String> items2 = getCidades("estados-cidades.json", estado);
                Spinner spinner2=(Spinner) findViewById(R.id.spn_cidade);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(curView, R.layout.spinner_layout, R.id.textView, items2);
                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayList<String> items2 = getCidades("estados-cidades.json", "AC");
        Spinner spinner2=(Spinner) findViewById(R.id.spn_cidade);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, items2);
        spinner2.setAdapter(adapter2);

        Button btnContinuar = (Button) findViewById(R.id.btn_cadastrar);
        btnContinuar.setOnClickListener(listener);

        Button btnCadastrarCliente = (Button) findViewById(R.id.btn_cancelar);
        btnCadastrarCliente.setOnClickListener(listener);
    }

    public ArrayList<String> getEstados(String jsonFile) {
        JSONArray jsonArray = null;
        ArrayList<String> estadosList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(jsonFile);
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");

            JSONObject object = new JSONObject(json);
            jsonArray  = object.getJSONArray("estados");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                estadosList.add(jsonArray.getJSONObject(i).getString("sigla"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return estadosList;
    }

    public ArrayList<String> getCidades(String jsonFile, String estado) {
        JSONArray jsonEstados = null;
        JSONObject jsonEstado = null;
        JSONArray jsonCidades = null;
        String nomeCidade;
        String nomeEstado;
        ArrayList<String> cidadesList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(jsonFile);
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");


            JSONObject object = new JSONObject(json);
            jsonEstados = object.getJSONArray("estados");

            for (int i = 0; i < jsonEstados.length(); i++) {
                nomeEstado = jsonEstados.getJSONObject(i).getString("sigla");

                if(nomeEstado.equals(estado)) {
                    Log.d("STATE", jsonEstados.getJSONObject(i).getString("nome"));
                    Log.d("STATE", jsonEstados.getJSONObject(i).getString("sigla"));

                    jsonEstado = jsonEstados.getJSONObject(i);
                    break;
                }
            }

            //Log.d("STATE", jsonEstado.toString());
            jsonCidades = jsonEstado.getJSONArray("cidades");

            for (int i = 0; i < jsonCidades.length(); i++) {
                nomeCidade = jsonCidades.getString(i);
                cidadesList.add(nomeCidade);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return cidadesList;
    }

    public void cancelar(){
        finish();
    }

    public void voltar(View v) {
        super.onBackPressed();
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
                .baseUrl(REGISTER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

    private void registerAttemptWithRetrofit(final String email,
                                              String senha,
                                              String nome,
                                              String dataNascimento,
                                              String cpf,
                                              String telRes,
                                              String telCom,
                                              String telCom2,
                                              String cep,
                                              String estado,
                                              String cidade,
                                              String logradouro,
                                              String bairro,
                                              String numero){
        ApiInterface mApiService = this.getInterfaceService();
        Call<Usuario> mService = mApiService.userCreate(email,
                                                        senha,
                                                        nome,
                                                        dataNascimento,
                                                        cpf,
                                                        telRes,
                                                        telCom,
                                                        telCom2,
                                                        cep,
                                                        estado,
                                                        cidade,
                                                        logradouro,
                                                        bairro,
                                                        numero);
        mService.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    Log.d("Response Failed", response.message());
                    Toast.makeText(getApplicationContext(), "Login e/ou senha incorreto(s)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    Log.d("StatusCode", Integer.toString(statusCode));

                    Usuario mUsuarioObject = response.body();
                    UserData mUserDataObject;
                    String returnedResponse = null;

                    if(mUsuarioObject != null) {
                        try {
                            mUserDataObject = mUsuarioObject.getUserData();

                            if(mUserDataObject != null){
                                returnedResponse = mUserDataObject.toString();
                                Log.d("Response String", returnedResponse);
                            } else {
                                Log.d("Response Error", "UserData is null");
                            }

                        } catch(Exception e) {
                            Log.d("Response Error", e.toString());
                        }
                    } else {
                        Log.d("Response Error", "Usuario is null");
                    }


                    if(returnedResponse == null) {
                        Log.d("Response Error", "Deu MUITO ruim.");
                        Toast.makeText(CadastroVendedor.this, "Aconteceu algo inesperado.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(CadastroVendedor.this, Drawer.class);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                call.cancel();
                Toast.makeText(CadastroVendedor.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void onClick(View v) {
        Button btn_proximo = (Button) findViewById(R.id.btn_proximo);
        Intent it = new Intent(this, CadastroVendedor.class);
        startActivity(it);
    }*/
}