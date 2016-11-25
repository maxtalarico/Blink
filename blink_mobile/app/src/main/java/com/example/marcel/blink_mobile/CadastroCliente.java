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
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastroCliente extends ActionBarActivity {
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
    private EditText celularEdTex;
    private EditText telComEdTex;

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
    private String celular;
    private String telCom;

    private String cep;
    private int estado;
    private int  cidade;
    private String logradouro;
    private String bairro;
    private String numero;

	View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCadastrarCliente:
                    cadastrarCliente();
                    break;
                case R.id.btn_cancelar:
                    cancelar();
                    break;
                default:
                    break;
            }
        }
    };

    protected void cadastrarCliente() {
        /*emailEdTex = (EditText) findViewById(R.id.campoEmail);
        //emailConfEdTex = (EditText) findViewById(R.id.txt_confirmar_email);

        senhaEdTex = (EditText) findViewById(R.id.txt_senha);
        senhaConfEdTex = (EditText) findViewById(R.id.txt_confirmar_senha);

        nomeEdTex = (EditText) findViewById(R.id.campoNome);
        dataNascimentoEdTex = (EditText) findViewById(R.id.txt_data_nascimento);
        cpfEdTex = (EditText) findViewById(R.id.campoCPF);
        telResEdTex = (EditText) findViewById(R.id.txt_telefone_residencial);
        celularEdTex = (EditText) findViewById(R.id.txt_teefone_comercial);
        telComEdTex = (EditText) findViewById(R.id.txt_telefone_celular);

        cepEdTex = (EditText) findViewById(R.id.txt_cep);
        estadoSpinner = (Spinner) findViewById(R.id.spn_estado);
        cidadeSpinner = (Spinner) findViewById(R.id.spn_cidade);
        logradouroEdTex = (EditText) findViewById(R.id.txt_endereco);
        bairroEdTex = (EditText) findViewById(R.id.txt_bairro);
        numeroEdTex = (EditText) findViewById(R.id.txt_numero);

        email = emailEdTex.getText().toString().trim();
        emailConf = emailConfEdTex.getText().toString().trim();

        senha = senhaEdTex.getText().toString().trim();
        senhaConf = senhaConfEdTex.getText().toString().trim();

        nome = nomeEdTex.getText().toString().trim();
        dataNascimento = dataNascimentoEdTex.getText().toString().trim();
        cpf = cpfEdTex.getText().toString().trim();
        telRes = telResEdTex.getText().toString().trim();
        celular = celularEdTex.getText().toString().trim();

        cep = cepEdTex.getText().toString().trim();;
        estado = getEstadoId(estadoSpinner.getSelectedItem().toString().trim());
        cidade = getCidadeId(cidadeSpinner.getSelectedItem().toString().trim(), estado);
        logradouro = logradouroEdTex.getText().toString().trim();;
        bairro = bairroEdTex.getText().toString().trim();;
        numero = numeroEdTex.getText().toString().trim();;*/

        registerAttemptWithRetrofit(/*email,
                                    emailConf,
                                    senha,
                                    senhaConf,
                                    nome,
                                    dataNascimento,
                                    cpf,
                                    telRes,
                                    celular,
                                    cep,
                                    estado,
                                    cidade,
                                    logradouro,
                                    bairro,
                                    numero*/);
    }

    protected int getEstadoId(String siglaEstado) {
        int idEstado = 0;
        JSONArray jsonEstados = null;
        String siglaEstadoCur;
        ArrayList<String> estadosList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open("cidades-estados-wid.json");
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");

            JSONObject object = new JSONObject(json);
            jsonEstados  = object.getJSONArray("estados");

            for (int i = 0; i < jsonEstados.length(); i++) {
                siglaEstadoCur = jsonEstados.getJSONObject(i).getString("sigla");

                //Log.d("Siglas Estado", "getEstadoId: " + siglaEstadoCur);

                if(siglaEstadoCur.equals(siglaEstado)) {
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("nome"));
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("sigla"));

                    idEstado = jsonEstados.getJSONObject(i).getInt("id");
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }

        return idEstado;
    }

    protected int getCidadeId(String nomeCidade, int idEstado) {
        int idCidade = 0;

        JSONArray jsonEstados = null;
        JSONObject jsonEstado = null;
        JSONArray jsonCidades = null;
        String nomeCidadeCur;
        int idEstadoCur;
        ArrayList<String> cidadesList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open("cidades-estados-wid.json");
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");


            JSONObject object = new JSONObject(json);
            jsonEstados = object.getJSONArray("estados");

            for (int i = 0; i < jsonEstados.length(); i++) {
                idEstadoCur = jsonEstados.getJSONObject(i).getInt("id");

                if(idEstadoCur == idEstado) {
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("nome"));
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("sigla"));

                    jsonEstado = jsonEstados.getJSONObject(i);
                    break;
                }
            }

            //Log.d("STATE", jsonEstado.toString());
            jsonCidades = jsonEstado.getJSONArray("cidades");

            for (int i = 0; i < jsonCidades.length(); i++) {
                nomeCidadeCur = jsonCidades.getJSONObject(i).getString("nome");

                if(nomeCidadeCur.equals(nomeCidade)) {
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("nome"));
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("sigla"));

                    idCidade = jsonCidades.getJSONObject(i).getInt("id");
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }

        return idCidade;
    }
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        ArrayList<String> estados = getEstados("estados-cidades.json");
        final Spinner spinnerEstados=(Spinner) this.findViewById(R.id.spinnerEstado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, estados);
        spinnerEstados.setAdapter(adapter);

        final Activity curView = this;

        spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estado = spinnerEstados.getSelectedItem().toString();;

                ArrayList<String> cidades = getCidades("estados-cidades.json", estado);
                Spinner spinnerCidades=(Spinner) findViewById(R.id.spinnerCidade);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(curView, R.layout.spinner_layout, R.id.textView, cidades);
                spinnerCidades.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayList<String> cidades = getCidades("estados-cidades.json", "AC");
        Spinner spinnerCidades=(Spinner) findViewById(R.id.spinnerCidade);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, cidades);
        spinnerCidades.setAdapter(adapter2);

        ArrayList<String> bancos = getBandeiras("bandeiras.json");
        Spinner spinnerBancos=(Spinner) findViewById(R.id.spinnerBandeira);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, bancos);
        spinnerBancos.setAdapter(adapter3);
		
		Button btnContinuar = (Button) findViewById(R.id.btnCadastrarCliente);
        btnContinuar.setOnClickListener(listener);

        /*Button btnCadastrarCliente = (Button) findViewById(R.id.btn_cancelar);
        btnCadastrarCliente.setOnClickListener(listener);*/
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
                nomeCidade = jsonCidades.getJSONObject(i).getString("nome");
                cidadesList.add(nomeCidade);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return cidadesList;
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

    private void registerAttemptWithRetrofit(/*String email,
                                              String emailConf,
                                              String senha,
                                              String senhaConf,
                                              String nome,
                                              String dataNascimento,
                                              String cpf,
                                              String telRes,
                                              String celular,
                                              String cep,
                                              int estado,
                                              int cidade,
                                              String logradouro,
                                              String bairro,
                                              String numero*/){
        email = "cliente@teste.com";
        emailConf = "cliente@teste.com";
        senha = "123456";
        senhaConf = "123456";
        nome = "Cliente Teste";
        dataNascimento = "12/11/1993";
        cpf = "423.370.988.00";
        telRes = "(19) 3852-4091";
        celular = "(19) 3852-94091";
        cep = "13920-000";
        logradouro = "Rua Teste";
        bairro = "Bairro Teste";
        numero = "22";
        cidade = 1;
        estado = 1;


        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = format.parse(dataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Integer id = null;

        Vendedor vendedor = null;
        Cliente cliente = new Cliente(id, celular, cpf, data, nome, telRes);
        Endereco endereco = new Endereco(id, bairro, cidade, estado, logradouro, numero, cep);

        Usuario usuario = new Usuario(id, vendedor, cliente, endereco, email, senha, nome);



        ApiInterface mApiService = this.getInterfaceService();
        Call<Usuario> mService = mApiService.userCreate(usuario.getUserData());
        mService.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getApplicationContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Usuario mUsuarioObject = response.body();

                    if(mUsuarioObject == null) {
                        Log.d("Response Error", "Deu MUITO ruim.");
                        Toast.makeText(CadastroCliente.this, "Aconteceu algo inesperado.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(CadastroCliente.this, Main.class);
                        Toast.makeText(CadastroCliente.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                call.cancel();
                Toast.makeText(CadastroCliente.this, "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void onClick(View v) {
        Button btn_proximo = (Button) findViewById(R.id.btn_proximo);
        Intent it = new Intent(this, CadastroVendedor.class);
        startActivity(it);
    }*/
}