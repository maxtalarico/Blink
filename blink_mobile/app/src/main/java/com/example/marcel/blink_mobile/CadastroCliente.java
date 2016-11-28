package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroCliente extends ActionBarActivity implements TextWatcher {
    //implements View.OnClickListener
    final String REGISTER_URL = "http://blink-brunopansani-1.c9users.io/";

    public EditText Enome, Eemail, EemailConf, EcpfCliente, Esenha, EsenhaConf, EdataNasc, EtelCliente, Ecelular,
            EcepCliente, Eendereco, Enumero, Ebairro, EnumCartao, EnomeCartao, Ecodigo, Enumpin;
    public String Snome, Semail, SemailConf, ScpfCliente, Ssenha, SsenhaConf, SdataNasc, StelCliente, Scelular,
            ScepCliente, Slogradouro, Snumero, Sbairro, SnumCartao, SnomeCartao, Scodigo, Snumpin;
    public int Sestado, Scidade;

    Spinner cidadeSpinner;
    Spinner estadoSpinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        ArrayList<String> items = getEstados("cidades-estados-wid.json");
        estadoSpinner=(Spinner) this.findViewById(R.id.spinnerEstado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, items);
        estadoSpinner.setAdapter(adapter);

        final Activity curView = this;

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estado = estadoSpinner.getSelectedItem().toString();;

                ArrayList<String> items2 = getCidades("cidades-estados-wid.json", estado);
                cidadeSpinner=(Spinner) findViewById(R.id.spinnerCidade);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(curView, R.layout.spinner_layout, R.id.textView, items2);
                cidadeSpinner.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayList<String> items2 = getCidades("cidades-estados-wid", "AC");
        cidadeSpinner=(Spinner) findViewById(R.id.spinnerCidade);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, items2);
        cidadeSpinner.setAdapter(adapter2);

        Button btnContinuar = (Button) findViewById(R.id.btnCadastrarCliente);
        btnContinuar.setOnClickListener(listener);

        Button btnCadastrarCliente = (Button) findViewById(R.id.btnCancelarCadastro);
        btnCadastrarCliente.setOnClickListener(listener);

        //registerViews();
    }

    public void registerViews(){
            Enome = (EditText) findViewById(R.id.campoNome);
            Eemail = (EditText) findViewById(R.id.campoEmail);
            EemailConf = (EditText) findViewById(R.id.campoEmailConf);
            EcpfCliente = (EditText) findViewById(R.id.campoCPF);
            Esenha = (EditText) findViewById(R.id.campoSenha);
            EsenhaConf = (EditText) findViewById(R.id.campoSenhaConf);
            EtelCliente = (EditText) findViewById(R.id.campoTelefone);
            Ecelular = (EditText) findViewById(R.id.campoCelular);
            EcepCliente = (EditText) findViewById(R.id.campoCEP);
            Eendereco = (EditText) findViewById(R.id.campoEndereco);
            Enumero = (EditText) findViewById(R.id.campoNum);
            Ebairro = (EditText) findViewById(R.id.campoBairro);
            Enumpin = (EditText) findViewById(R.id.campoPIN);

            EdataNasc = (EditText) findViewById(R.id.campoDDMM);

            Enome.addTextChangedListener(this);
            Eemail.addTextChangedListener(this);
            EemailConf.addTextChangedListener(this);
            EcpfCliente.addTextChangedListener(this);
            Esenha.addTextChangedListener(this);
            EsenhaConf.addTextChangedListener(this);
            EtelCliente.addTextChangedListener(this);
            Ecelular.addTextChangedListener(this);
            EcepCliente.addTextChangedListener(this);
            Eendereco.addTextChangedListener(this);
            Enumero.addTextChangedListener(this);
            Ebairro.addTextChangedListener(this);
            Enumpin.addTextChangedListener(this);
            EdataNasc.addTextChangedListener(this);

            estadoSpinner = (Spinner) findViewById(R.id.spinnerEstado);
            cidadeSpinner = (Spinner) findViewById(R.id.spinnerCidade);

            MaskEditTextChangedListener maskTel = new MaskEditTextChangedListener("(##)####-#####", EtelCliente);
            MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", EcpfCliente);
            MaskEditTextChangedListener maskNasc = new MaskEditTextChangedListener("##/##/####", EdataNasc);
            MaskEditTextChangedListener maskCep = new MaskEditTextChangedListener("#####-###", EcepCliente);

            EtelCliente.addTextChangedListener(maskTel);
            EcpfCliente.addTextChangedListener(maskCPF);
            EdataNasc.addTextChangedListener(maskNasc);
            EcepCliente.addTextChangedListener(maskCep);

            Snome = Enome.getText().toString().trim();
            Semail = Eemail.getText().toString().trim();
            SemailConf = EemailConf.getText().toString().trim();
            ScpfCliente = EcpfCliente.getText().toString().trim();
            Ssenha = Esenha.getText().toString().trim();
            SsenhaConf = EsenhaConf.getText().toString().trim();
            SdataNasc = EdataNasc.getText().toString().trim();
            StelCliente = EtelCliente.getText().toString().trim();
            Scelular = Ecelular.getText().toString().trim();
            ScepCliente = EcepCliente.getText().toString().trim();
            Slogradouro = Eendereco.getText().toString().trim();
            Snumero = Enumero.getText().toString().trim();
            Sbairro = Ebairro.getText().toString().trim();
            Snumpin = Enumpin.getText().toString().trim();
            Sestado = getEstadoId(estadoSpinner.getSelectedItem().toString().trim());
            Scidade = getCidadeId(cidadeSpinner.getSelectedItem().toString().trim(), Sestado);

    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Criando conta", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(Enome)) ret = false;
        if (!Validation.hasText(EcpfCliente)) ret = false;
        if (!Validation.hasText(Esenha)) ret = false;
        if (!Validation.hasText(EsenhaConf)) ret = false;
        if (!Validation.hasText(EdataNasc)) ret = false;
        if (!Validation.hasText(EtelCliente)) ret = false;
        if (!Validation.hasText(Ecelular)) ret = false;
        if (!Validation.hasText(EcepCliente)) ret = false;
        if (!Validation.hasText(Eendereco)) ret = false;
        if (!Validation.hasText(Enumero)) ret = false;
        if (!Validation.hasText(Ebairro)) ret = false;
        /*if (!Validation.hasText(EnumCartao)) ret = false;
        if (!Validation.hasText(EnomeCartao)) ret = false;
        if (!Validation.hasText(Ecodigo)) ret = false;*/
        if (!Validation.isEmailAddress(Eemail, true)) ret = false;
        if (!Validation.isEmailAddress(EemailConf, true)) ret = false;

        return ret;
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCadastrarCliente:
                    if (checkValidation()) {
                        cadastrarCliente();
                    }
                    break;
                case R.id.btnCancelarCadastro:
                    cancelar();
                    break;
                default:
                    break;
            }
        }
    };

    private void cancelar() {
        finish();
    }

    protected void cadastrarCliente() {
        registerViews();

        if(checkValidation()) {

            registerAttemptWithRetrofit(Snome,
                    Semail,
                    SemailConf,
                    ScpfCliente,
                    Ssenha,
                    SsenhaConf,
                    SdataNasc,
                    StelCliente,
                    Scelular,
                    ScepCliente,
                    Scidade,
                    Sestado,
                    Slogradouro,
                    Snumero,
                    Sbairro);
        }
    }

   /*private void registerAttemptWithRetrofit(String Snome, String Semail, String SemailConf, String ScpfCliente, String Ssenha, String SsenhaConf, String SdataNasc, String StelCliente, String Scelular,
            String ScepCliente, String Sendereco, String Snumero, String Sbairro, String SnumCartao, String SnomeCartao, String Scodigo, String Snumpin){

        Integer id = null;

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        //final Integer contaBancaria = b.getInt("idConta");

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Cliente cliente = userData.getCliente();

        Endereco endereco = new Endereco(null, bairro, cidade, estado, logradouro, numero, cep);

        List<CadastroCliente> clinteList = new ArrayList<>();

        CadastroCliente cadastroCliente = new CadastroCliente(Snome, Semail, SemailConf, ScpfCliente, Ssenha, SsenhaConf, SdataNasc, StelCliente, Scelular,
                ScepCliente, Sendereco, Snumero, Sbairro, SnumCartao, SnomeCartao, Scodigo, Snumpin, cliente.getId());
        clinteList.add(cadastroCliente);

        Cliente[] cliente = new Cliente[clinteList.size()];
        cliente = clinteList.toArray(cliente);

        vendedor.setcliente(cliente);

        ApiInterface mApiService = this.getInterfaceService();
        createClienteCall(mApiService, cadastroCliente, endereco);

    }*/

    /*private void createEnderecoCall(final ApiInterface mApiService, final EstabelecimentoComercial estabelecimentoComercial, Endereco endereco) {
        Call<Endereco> mServiceUsuario = mApiService.createEndereco(endereco);
        mServiceUsuario.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    //Toast.makeText(getActivity(), "Tente novamente. Usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Endereco endereco = response.body();

                    cliente.setLocalizacao(endereco.getId());
                    createClienteCall(mApiService, cliente);

                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                call.cancel();
                Toast.makeText(CadastroCliente.this, "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    /*private void createClienteCall(ApiInterface mApiService, Cliente cliente) {
        Call<Cliente> mService = mApiService.estabelecimentoCreate(cliente);
        mService.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                int statusCode;

                if (!response.isSuccessful()) {
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(CadastroCliente.this, "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Cliente mClienteObject = response.body();
                }
            }
        });

    }*/


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

    private void registerAttemptWithRetrofit(String Snome,
                                             String Semail,
                                             String SemailConf,
                                             String ScpfCliente,
                                             String Ssenha,
                                             String SsenhaConf,
                                             String SdataNasc,
                                             String StelCliente,
                                             String Scelular,
                                             String ScepCliente,
                                             Integer Scidade,
                                             Integer Sestado,
                                             String Sendereco,
                                             String Snumero,
                                             String Sbairro) {
        //email = "cliente@teste.com";
        //emailConf = "cliente@teste.com";
        //senha = "123456";
        //senhaConf = "123456";
        //nome = "Cliente Teste";
        //dataNascimento = "12/11/1993";
        //cpf = "423.370.988.00";
        //telRes = "(19) 3852-4091";
        //celular = "(19) 3852-94091";
        //cep = "13920-000";
        //logradouro = "Rua Teste";
        //bairro = "Bairro Teste";
        //numero = "22";
        //cidade = 1;
        //estado = 1;


        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = format.parse(SdataNasc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Integer id = null;

        Vendedor vendedor = null;
        Cliente cliente = new Cliente(id, Scelular, ScpfCliente, data, Snome, StelCliente);
        Endereco endereco = new Endereco(id, Sbairro, Scidade, Sestado, Sendereco, Snumero, ScepCliente);

        Usuario usuario = new Usuario(id, vendedor, cliente, endereco, Semail, Ssenha, Snome);


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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


        Validation.hasText(Enome);
        Validation.hasText(Eemail);
        Validation.hasText(EemailConf);
        Validation.isEmailAddress(Eemail, true);
        Validation.isEmailAddress(EemailConf, true);
        if (!(EemailConf.getText().toString().equals(Eemail.getText().toString())))
            EsenhaConf.setError("Email e confirmação de email não correspondem");
        Validation.hasText(EcpfCliente);
        if (EcpfCliente.length() < 14) {
            EcpfCliente.setError("Deve conter 11 dígitos");
        }
        Validation.hasText(Esenha);
        if (Esenha.length() < 6) {
            Esenha.setError("Deve conter no mínimo 6 dígitos");
        }
        Validation.hasText(EsenhaConf);
        if (!(EsenhaConf.getText().toString().equals(Esenha.getText().toString())))
           EsenhaConf.setError("Senha e confirmação de senha não correspondem");
        Validation.hasText(EdataNasc);
        if (EdataNasc.length() < 8) {
        EdataNasc.setError("Deve conter dd/mm/aaaa");
        }
        Validation.hasText(EtelCliente);
        if (EtelCliente.length() < 10) {
            EtelCliente.setError("Deve conter no mínimo 10 dígitos");
        }
        Validation.hasText(Ecelular);
        if (Ecelular.length() < 10) {
            Ecelular.setError("Deve conter no mínimo 10 dígitos");
        }
        Validation.hasText(EcepCliente);
        Validation.hasText(Ebairro);
        Validation.hasText(Eendereco);
        Validation.hasText(Enumero);
        Validation.hasText(Enumpin);
        if (Enumpin.length() < 4) {
            Enumpin.setError("Deve conter no mínimo 4 numeros");
        }

    }



    /*public void onClick(View v) {
        Button btn_proximo = (Button) findViewById(R.id.btn_proximo);
        Intent it = new Intent(this, CadastroVendedor.class);
        startActivity(it);
    }*/
}


