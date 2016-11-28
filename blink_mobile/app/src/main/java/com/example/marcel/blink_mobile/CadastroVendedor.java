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

public class CadastroVendedor extends ActionBarActivity  implements TextWatcher{
//implements View.OnClickListener
    final String REGISTER_URL = "http://blink-brunopansani-1.c9users.io/";

    public EditText Enome, Eemail, EemailConf, Ecpf, Esenha, EsenhaConf, EdataNasc, Etel, Etel2, Ecelular,
            Ecep, Eendereco, Enumero, Ebairro;
    public String Snome, Semail, SemailConf, Scpf, Ssenha, SsenhaConf, SdataNasc, Stel, Stel2, Scelular,
            Scep, Sendereco, Snumero, Sbairro;
    public int Sestado, Scidade;

    Spinner estadoSpinner;
    Spinner cidadeSpinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vendedor);

        ArrayList<String> items = getEstados("cidades-estados-wid.json");
        estadoSpinner=(Spinner) this.findViewById(R.id.spn_estado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, items);
        estadoSpinner.setAdapter(adapter);

        final Activity curView = this;

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estado = estadoSpinner.getSelectedItem().toString();;

                ArrayList<String> items2 = getCidades("cidades-estados-wid.json", estado);
                cidadeSpinner=(Spinner) findViewById(R.id.spn_cidade);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(curView, R.layout.spinner_layout, R.id.textView, items2);
                cidadeSpinner.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayList<String> items2 = getCidades("cidades-estados-wid", "AC");
        cidadeSpinner=(Spinner) findViewById(R.id.spn_cidade);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, items2);
        cidadeSpinner.setAdapter(adapter2);

        Button btnContinuar = (Button) findViewById(R.id.btn_cadastrar);
        btnContinuar.setOnClickListener(listener);

        Button btnCadastrarCliente = (Button) findViewById(R.id.btn_cancelar);
        btnCadastrarCliente.setOnClickListener(listener);
    }

    public void registerViews(){
        Eemail = (EditText) findViewById(R.id.txt_email);
        EemailConf = (EditText) findViewById(R.id.txt_confirmar_email);
        Esenha = (EditText) findViewById(R.id.txt_senha);
        EsenhaConf = (EditText) findViewById(R.id.txt_confirmar_senha);
        Enome = (EditText) findViewById(R.id.txt_nome);
        EdataNasc = (EditText) findViewById(R.id.txt_data_nascimento);
        Ecpf = (EditText) findViewById(R.id.txt_cpf);
        Etel = (EditText) findViewById(R.id.txt_telefone_residencial);
        Etel2 = (EditText) findViewById(R.id.txt_teefone_comercial);
        Ecelular = (EditText) findViewById(R.id.txt_telefone_celular);
        Ecep = (EditText) findViewById(R.id.txt_cep);
        estadoSpinner = (Spinner) findViewById(R.id.spn_estado);
        cidadeSpinner = (Spinner) findViewById(R.id.spn_cidade);
        Eendereco = (EditText) findViewById(R.id.txt_endereco);
        Ebairro = (EditText) findViewById(R.id.txt_bairro);
        Enumero = (EditText) findViewById(R.id.txt_numero);


        Enome.addTextChangedListener(this);
        Eemail.addTextChangedListener(this);
        EemailConf.addTextChangedListener(this);
        Ecpf.addTextChangedListener(this);
        Esenha.addTextChangedListener(this);
        EsenhaConf.addTextChangedListener(this);
        Etel.addTextChangedListener(this);
        Etel2.addTextChangedListener(this);
        Ecelular.addTextChangedListener(this);
        Ecep.addTextChangedListener(this);
        Eendereco.addTextChangedListener(this);
        Enumero.addTextChangedListener(this);
        Ebairro.addTextChangedListener(this);
        EdataNasc.addTextChangedListener(this);

        estadoSpinner = (Spinner) findViewById(R.id.spinnerEstado);
        cidadeSpinner = (Spinner) findViewById(R.id.spinnerCidade);

        MaskEditTextChangedListener maskTel = new MaskEditTextChangedListener("(##)####-#####", Etel);
        MaskEditTextChangedListener maskTel2 = new MaskEditTextChangedListener("(##)####-#####", Etel2);
        MaskEditTextChangedListener maskCel = new MaskEditTextChangedListener("(##)####-#####", Ecelular);
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", Ecpf);
        MaskEditTextChangedListener maskNasc = new MaskEditTextChangedListener("##/##/####", EdataNasc);
        MaskEditTextChangedListener maskCep = new MaskEditTextChangedListener("#####-###", Ecep);

        Etel.addTextChangedListener(maskTel);
        Etel2.addTextChangedListener(maskTel2);
        Ecelular.addTextChangedListener(maskCel);
        Ecpf.addTextChangedListener(maskCPF);
        EdataNasc.addTextChangedListener(maskNasc);
        Ecep.addTextChangedListener(maskCep);

        Semail = Eemail.getText().toString().trim();
        SemailConf = EemailConf.getText().toString().trim();
        Ssenha = Esenha.getText().toString().trim();
        SsenhaConf = EsenhaConf.getText().toString().trim();
        Snome = Enome.getText().toString().trim();
        SdataNasc = EdataNasc.getText().toString().trim();
        Scpf = Ecpf.getText().toString().trim();
        Stel = Etel.getText().toString().trim();
        Stel2 = Etel2.getText().toString().trim();
        Scelular = Ecelular.getText().toString().trim();
        Scep = Ecep.getText().toString().trim();
        Sestado = getEstadoId(estadoSpinner.getSelectedItem().toString().trim());
        Scidade = getCidadeId(cidadeSpinner.getSelectedItem().toString().trim(), Sestado);
        Sendereco = Eendereco.getText().toString().trim();
        Sbairro = Ebairro.getText().toString().trim();
        Snumero = Enumero.getText().toString().trim();

    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Criando conta", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(Enome)) ret = false;
        if (!Validation.hasText(Ecpf)) ret = false;
        if (!Validation.hasText(Esenha)) ret = false;
        if (!Validation.hasText(EsenhaConf)) ret = false;
        if (!Validation.hasText(EdataNasc)) ret = false;
        if (!Validation.hasText(Etel)) ret = false;
        if (!Validation.hasText(Etel2)) ret = false;
        if (!Validation.hasText(Ecelular)) ret = false;
        if (!Validation.hasText(Ecep)) ret = false;
        if (!Validation.hasText(Eendereco)) ret = false;
        if (!Validation.hasText(Enumero)) ret = false;
        if (!Validation.hasText(Ebairro)) ret = false;
        if (!Validation.isEmailAddress(Eemail, true)) ret = false;
        if (!Validation.isEmailAddress(EemailConf, true)) ret = false;

        return ret;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cadastrar:
                    if (checkValidation()) {
                        cadastrarVendedor();
                    }
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
        registerViews();

        if(checkValidation()) {
            registerAttemptWithRetrofit(Semail,
                                        SemailConf,
                                        Ssenha,
                                        SsenhaConf,
                                        Snome,
                                        SdataNasc,
                                        Scpf,
                                        Stel,
                                        Scelular,
                                        Stel2,
                                        Scep,
                                        Sestado,
                                        Scidade,
                                        Sendereco,
                                        Sbairro,
                                        Snumero);
        }


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
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("nome"));
                    //Log.d("STATE", jsonEstados.getJSONObject(i).getString("sigla"));

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

   private void registerAttemptWithRetrofit(String Semail,
                                            String  SemailConf,
                                            String  Ssenha,
                                            String  SsenhaConf,
                                            String  Snome,
                                            String  SdataNasc,
                                            String  Scpf,
                                            String  Stel,
                                            String  Scelular,
                                            String  Stel2,
                                            String  Scep,
                                            int  Sestado,
                                            int  Scidade,
                                            String Slogradouro,
                                            String Sbairro,
                                            String Snumero){
       /* email = "vendor99@teste.com";
        emailConf = "vendor99@teste.com";
        senha = "123456";
        senhaConf = "123456";
        nome = "Vendor Teste";
        dataNascimento = "12/11/1993";
        cpf = "423.370.988.00";
        telRes = "(19) 3852-4091";
        celular = "(19) 3852-94091";
        telCom = "(19) 3852-4091";
        cep = "13920-000";
        logradouro = "Rua Teste";
        bairro = "Bairro Teste";
        numero = "22";
        cidade = 1;
        estado = 1;*/

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = format.parse(SdataNasc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Integer id = null;

       // Vendedor vendedor = new Vendedor(id, celular, cpf, data, nome, telCom, telRes);

        Vendedor vendedor = null;
        Cliente cliente = null;
        Endereco endereco = new Endereco(id, Sbairro, Scidade, Sestado, Slogradouro, Snumero, Scep);

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
                        Toast.makeText(CadastroVendedor.this, "Aconteceu algo inesperado.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(CadastroVendedor.this, Main.class);
                        Toast.makeText(CadastroVendedor.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                call.cancel();
                Toast.makeText(CadastroVendedor.this, "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
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
            EemailConf.setError("Email e confirmação de email não correspondem");
        Validation.hasText(Ecpf);
        if (Ecpf.length() < 14) {
            Ecpf.setError("Deve conter 11 dígitos");
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
        Validation.hasText(Etel);
        if (Etel.length() < 10) {
            Etel.setError("Deve conter no mínimo 10 dígitos");
        }
        Validation.hasText(Etel2);
        if (Etel2.length() < 10) {
            Etel2.setError("Deve conter no mínimo 10 dígitos");
        }
        Validation.hasText(Ecelular);
        if (Ecelular.length() < 10) {
            Ecelular.setError("Deve conter no mínimo 10 dígitos");
        }
        Validation.hasText(Ecep);
        Validation.hasText(Ebairro);
        Validation.hasText(Eendereco);
        Validation.hasText(Enumero);

    }
}