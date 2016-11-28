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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.ContaBancaria;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
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

public class CadastroEstabelecimentoComercial extends Fragment  {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";
    String categoria;
    int estado;
    int cidade;

    Spinner cidadeSpinner;
    Spinner estadoSpinner;
    Spinner contaSpinner;


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cadastro_estabelecimentos_comercial, container, false);

        //Spinner Categoria

        ArrayList<String> categorias = getCategorias("categorias.json");
        final Spinner spinnerCategorias=(Spinner) view.findViewById(R.id.spn_categoria);
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, categorias);
        spinnerCategorias.setAdapter(adapterCategorias);
        categoria = spinnerCategorias.getSelectedItem().toString();

        //Spinner Estados

        ArrayList<String> estados = getEstados("cidades-estados-wid.json");
        estadoSpinner=(Spinner) view.findViewById(R.id.spn_estado_estabelecimento);
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, estados);
        estadoSpinner.setAdapter(adapterEstados);

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estado = estadoSpinner.getSelectedItem().toString();;

                ArrayList<String> cidades = getCidades("cidades-estados-wid.json", estado);
                cidadeSpinner=(Spinner) view.findViewById(R.id.spn_cidade_estabelecimento);
                ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, cidades);
                cidadeSpinner.setAdapter(adapterCidades);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //Spinner Cidades

        ArrayList<String> cidades = getCidades("cidades-estados-wid.json", "AC");
        cidadeSpinner=(Spinner) view.findViewById(R.id.spn_cidade_estabelecimento);
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, cidades);
        cidadeSpinner.setAdapter(adapterCidades);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String categoria = spinnerCategorias.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        setSpinnerContasBancarias(vendedor.getId());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                estado = getEstadoId(estadoSpinner.getSelectedItem().toString().trim());
                cidade = getCidadeId(cidadeSpinner.getSelectedItem().toString().trim(), estado);

                switch (v.getId()) {
                    case R.id.btn_cadastrar_estabelecimento:
                        EditText nomeEstab = (EditText) getView().findViewById(R.id.txt_nome_estabelecimento);
                        EditText cnpj = (EditText) getView().findViewById(R.id.txt_cnpj_estabelecimento);
                        EditText telEstab = (EditText) getView().findViewById(R.id.txt_telefone_estabelecimento);
                        EditText cep = (EditText) getView().findViewById(R.id.txt_cep_estabelecimento);
                        EditText endereco = (EditText) getView().findViewById(R.id.txt_endereco_estabelecimento);
                        EditText numero = (EditText) getView().findViewById(R.id.txt_numero_estabelecimento);
                        EditText bairro = (EditText) getView().findViewById(R.id.txt_bairro_estabelecimento);

                        if ("".equals(nomeEstab.getText().toString().trim())) {
                            nomeEstab.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Nome do Dispositivo é Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ("".equals(cnpj.getText().toString().trim())) {
                            cnpj.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Agência Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ("".equals(telEstab.getText().toString().trim())) {
                            telEstab.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }if ("".equals(cep.getText().toString().trim())) {
                        cep.setError("Campo obrigatório");
                        // Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                        return;
                        }if ("".equals(endereco.getText().toString().trim())) {
                            endereco.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }if ("".equals(numero.getText().toString().trim())) {
                            numero.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }if ("".equals(bairro.getText().toString().trim())) {
                            bairro.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            registerAttemptWithRetrofit(Integer.parseInt(categoria.split(" - ")[0]),
                                    cnpj.getText().toString(),
                                    1,
                                    nomeEstab.getText().toString(),
                                    telEstab.getText().toString(),
                                    cep.getText().toString(),
                                    endereco.getText().toString(),
                                    bairro.getText().toString(),
                                    numero.getText().toString(),
                                    estado,
                                    cidade);

                            fragment = new EstabelecimentosComerciais();
                        }
                        break;

                    case R.id.btn_cancelar:
                        fragment = new EstabelecimentosComerciais();
                        break;

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button btnCadastrar = (Button) view.findViewById(R.id.btn_cadastrar_estabelecimento);
        btnCadastrar.setOnClickListener(listener);

        Button btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(listener);

        EditText cnpj = (EditText) view.findViewById(R.id.txt_cnpj_estabelecimento);
        MaskEditTextChangedListener maskCnpj = new MaskEditTextChangedListener("##.###.###/####-##", cnpj);
        EditText foneEstab = (EditText) view.findViewById(R.id.txt_telefone_estabelecimento);
        MaskEditTextChangedListener maskTelEstab = new MaskEditTextChangedListener("(##)####-####", foneEstab);
        EditText cep = (EditText) view.findViewById(R.id.txt_cep_estabelecimento);
        MaskEditTextChangedListener maskCep = new MaskEditTextChangedListener("#####-###", cep);

        cnpj.addTextChangedListener(maskCnpj);
        foneEstab.addTextChangedListener(maskTelEstab);
        cep.addTextChangedListener(maskCep);

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

    private void registerAttemptWithRetrofit(Integer categoria,
                                             String cnpj,
                                             Integer codigoEstabelecimento,
                                             String nome,
                                             String telefoneCom,
                                             String cep,
                                             String logradouro,
                                             String bairro,
                                             String numero,
                                             Integer cidade,
                                             Integer estado){

        /*categoria = 1;
        cnpj = "6465432165465";
        codigoEstabelecimento = null;
        contaBancaria = 18;
        nome = "Estab Teste";
        telefoneCom = "(19) 9874-9874";
        cep = "13920-000";
        logradouro = "Rua Teste";
        bairro = "Bairro Teste";
        numero = "22";
        cidade = 1;
        estado = 1;*/



        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Integer id = null;

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        final Integer contaBancaria = b.getInt("idConta");

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        Endereco endereco = new Endereco(null, bairro, cidade, estado, logradouro, numero, cep);

        List<EstabelecimentoComercial> estabelecimentosList = new ArrayList<EstabelecimentoComercial>();

        EstabelecimentoComercial estabelecimentoComercial = new EstabelecimentoComercial(categoria, cnpj, codigoEstabelecimento, contaBancaria, id, null, nome, telefoneCom, vendedor.getId());
        estabelecimentosList.add(estabelecimentoComercial);

        EstabelecimentoComercial[] estabelecimentosComerciais = new EstabelecimentoComercial[estabelecimentosList.size()];
        estabelecimentosComerciais = estabelecimentosList.toArray(estabelecimentosComerciais);

        vendedor.setEstabelecimentoComercials(estabelecimentosComerciais);

        ApiInterface mApiService = this.getInterfaceService();
        createEnderecoCall(mApiService, estabelecimentoComercial, endereco);

    }

    private void createEnderecoCall(final ApiInterface mApiService, final EstabelecimentoComercial estabelecimentoComercial, Endereco endereco) {
        Call<Endereco> mServiceUsuario = mApiService.createEndereco(endereco);
        mServiceUsuario.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    //Toast.makeText(getActivity(), "Tente novamente. Usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Endereco endereco = response.body();

                    estabelecimentoComercial.setLocalizacao(endereco.getId());
                    createEstabelecimentoComercialCall(mApiService, estabelecimentoComercial);

                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createEstabelecimentoComercialCall(ApiInterface mApiService, EstabelecimentoComercial estabelecimentoComercial) {
        Call<EstabelecimentoComercial> mService = mApiService.estabelecimentoCreate(estabelecimentoComercial);
        mService.enqueue(new Callback<EstabelecimentoComercial>() {
            @Override
            public void onResponse(Call<EstabelecimentoComercial> call, Response<EstabelecimentoComercial> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    EstabelecimentoComercial mEstabelecimentoComercialObject = response.body();
                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<String> getCategorias(String jsonFile) {
        JSONArray jsonArray = null;
        ArrayList<String> categoriasList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(jsonFile);
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");

            JSONObject object = new JSONObject(json);
            jsonArray  = object.getJSONArray("categorias");

            for (int i = 0; i < jsonArray.length(); i++) {
                categoriasList.add(jsonArray.getJSONObject(i).getInt("id") + " - " + jsonArray.getJSONObject(i).getString("nome"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return categoriasList;
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

    public void setSpinnerContasBancarias(Integer idVendedor) {
        ApiInterface mApiService = this.getInterfaceService();
        Call<ContaBancaria[]> mService = mApiService.getContas(idVendedor);
        mService.enqueue(new Callback<ContaBancaria[]>() {
            @Override
            public void onResponse(Call<ContaBancaria[]> call, Response<ContaBancaria[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    ContaBancaria[] contas = response.body();

                    Activity activity = getActivity();
                    Intent i = activity.getIntent();
                    Bundle b = i.getExtras();

                    Usuario usuario = (Usuario) b.getSerializable("Usuario");
                    UserData userData = usuario.getUserData();
                    Vendedor vendedor = userData.getVendedor();

                    vendedor.setContasBancarias(contas);

                    getContas(contas);
                }
            }

            @Override
            public void onFailure(Call<ContaBancaria[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getContas(ContaBancaria[] contas) {
        View view = getView();

        try {
            int qtdContas = Array.getLength(contas);

            String nomeBanco;
            String numeroAgencia;
            Integer idConta;

            ArrayList<String> contasStrings = new ArrayList<String>();

            for (int x = 0; x < qtdContas; x++ ) {
                idConta = contas[x].getId();
                numeroAgencia = contas[x].getAgencia() + "-" +
                                contas[x].getDigitoAgencia() + " / " +
                                contas[x].getNumeroConta() + "-" +
                                contas[x].getDigitoConta();

                contasStrings.add(Integer.toString(idConta) + " - " + numeroAgencia);
            }

            Activity activity = getActivity();
            Intent i = activity.getIntent();

            contaSpinner = (Spinner) view.findViewById(R.id.spn_contas_bancarias);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, contasStrings);
            contaSpinner.setAdapter(adapter);

            String contaSpinnerString = contaSpinner.getSelectedItem().toString();
            Integer idContaBancaria = Integer.parseInt(contaSpinnerString.split(" - ")[0]);
            i.putExtra("idEstabelecimentoComercial", idContaBancaria);

            contaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Activity activity = getActivity();
                    Intent i = activity.getIntent();

                    String contaSpinnerString = contaSpinner.getSelectedItem().toString();
                    Integer idContaBancaria = Integer.parseInt(contaSpinnerString.split(" - ")[0]);
                    i.putExtra("idEstabelecimentoComercial", idContaBancaria);
                    Log.d("id", "onItemSelected: " + contaSpinnerString.split(" - ")[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        } catch (Exception e) {
            Log.d("Sem Contas", "Contas null: ");
        }
    }
}
