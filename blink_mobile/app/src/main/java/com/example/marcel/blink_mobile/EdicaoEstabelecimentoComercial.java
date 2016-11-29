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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercialEndereco;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tayna on 24/10/2016.
 */

public class EdicaoEstabelecimentoComercial extends Fragment/* implements View.OnClickListener*/ {
    final String REGISTER_URL = "http://blink-brunopansani-1.c9users.io/";

    View rootView;

    EditText nomeEdTex;
    EditText cnpjEdTex;
    EditText telefoneEdTex;
    EditText cepEdTex;
    EditText logradouroEdTex;
    EditText bairroEdTex;
    EditText numeroEdTex;
    EditText categoriaEdTex;

    /* EditText estadoSpinner = (Spinner) rootView.findViewById(R.id.spn_estado);
     EditText cidadeSpinner = (Spinner) rootView.findViewById(R.id.spn_cidade);*/

    Integer categoria;
    String cnpj;
    Integer codigoEstabelecimento;
    Integer contaBancaria;
    Integer localizacao;
    String nome;
    String telefoneCom;
    String cep;
    String logradouro;
    String bairro;
    String numero;
    Integer cidade = 1;
    Integer estado = 1;

    Integer idEstabelecimentoComercial;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_edicao_estabelecimentos_comercial, container, false);

        nomeEdTex = (EditText) rootView.findViewById(R.id.campoNome);
        cnpjEdTex = (EditText) rootView.findViewById(R.id.campoCNPJ);
        telefoneEdTex = (EditText) rootView.findViewById(R.id.campoTelefone);
        cepEdTex = (EditText) rootView.findViewById(R.id.campoCEP);
                       /* EditText estadoSpinner = (Spinner) rootView.findViewById(R.id.spn_estado);
                        EditText cidadeSpinner = (Spinner) rootView.findViewById(R.id.spn_cidade);*/
        logradouroEdTex = (EditText) rootView.findViewById(R.id.campoEndereco);
        bairroEdTex = (EditText) rootView.findViewById(R.id.campoBairro);
        numeroEdTex = (EditText) rootView.findViewById(R.id.campoNo);
        categoriaEdTex = (EditText) rootView.findViewById(R.id.campoCategoria);

        Bundle b = getArguments();
        idEstabelecimentoComercial = b.getInt("idEstabelecimento");

        getEstabelecimentoComercial(idEstabelecimentoComercial);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_alterar:

                        nomeEdTex = (EditText) rootView.findViewById(R.id.campoNome);
                        cnpjEdTex = (EditText) rootView.findViewById(R.id.campoCNPJ);
                        telefoneEdTex = (EditText) rootView.findViewById(R.id.campoTelefone);
                        categoriaEdTex = (EditText) rootView.findViewById(R.id.campoCategoria);
                        cepEdTex = (EditText) rootView.findViewById(R.id.campoCEP);
                       /* EditText estadoSpinner = (Spinner) rootView.findViewById(R.id.spn_estado);
                        EditText cidadeSpinner = (Spinner) rootView.findViewById(R.id.spn_cidade);*/
                        logradouroEdTex = (EditText) rootView.findViewById(R.id.campoEndereco);
                        bairroEdTex = (EditText) rootView.findViewById(R.id.campoBairro);
                        numeroEdTex = (EditText) rootView.findViewById(R.id.campoNo);

                        if ("".equals(telefoneEdTex.getText().toString().trim())) {
                            telefoneEdTex.setError("Campo obrigatório");
                            return;
                        }if ("".equals(cepEdTex.getText().toString().trim())) {
                        cepEdTex.setError("Campo obrigatório");
                        return;
                    }if ("".equals(logradouroEdTex.getText().toString().trim())) {
                        logradouroEdTex.setError("Campo obrigatório");
                        return;
                    }if ("".equals(bairroEdTex.getText().toString().trim())) {
                        bairroEdTex.setError("Campo obrigatório");
                        return;
                    }if ("".equals(numeroEdTex.getText().toString().trim())) {
                        numeroEdTex.setError("Campo obrigatório");
                        return;
                    }else {
                        registerAttemptWithRetrofit(categoria,
                                                    cnpjEdTex.getText().toString(),
                                                    idEstabelecimentoComercial,
                                                    1,
                                                    nomeEdTex.getText().toString(),
                                                    telefoneEdTex.getText().toString(),
                                                    cepEdTex.getText().toString(),
                                                    logradouroEdTex.getText().toString(),
                                                    bairroEdTex.getText().toString(),
                                                    numeroEdTex.getText().toString(),
                                                    cidade,
                                                    estado);

                        fragment = new EstabelecimentosComerciaisList();
                        //fragment = new EstabelecimentosComerciais();

                    }
                        break;

                    case R.id.btn_cancelar:
                        fragment = new EstabelecimentosComerciaisList();
                        //fragment = new EstabelecimentosComerciais();
                        break;

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button btnAlterar = (Button)rootView.findViewById(R.id.btn_alterar);
        btnAlterar.setOnClickListener(listener);

        Button btnCancelar= (Button)rootView.findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(listener);

        return rootView;
    }


/*    public void onClick(View v) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.btn_alterar:
                registerAttemptWithRetrofit();
                break;

            case R.id.btn_cancelar:
                fragment = new EstabelecimentosComerciais();
                break;

            default:
                break;
        }

        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }*/
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

    private void registerAttemptWithRetrofit(Integer categoria,
                                             String cnpj,
                                             Integer codigoEstabelecimento,
                                             Integer contaBancaria,
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
        cep = "13920-111";
        logradouro = "Rua TesteQ";
        bairro = "Bairro TesteW";
        numero = "22";
        cidade = 1;
        estado = 1;*/

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Integer id = null;

        Bundle bArguments = getArguments();
        Integer idEstabelecimentoComercial = bArguments.getInt("idEstabelecimento");

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        Endereco endereco = new Endereco(null, bairro, cidade, estado, logradouro, numero, cep);

        EstabelecimentoComercial estabelecimentoComercial = new EstabelecimentoComercial(categoria, cnpj, codigoEstabelecimento, contaBancaria, idEstabelecimentoComercial, null, nome, telefoneCom, vendedor.getId());

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
                    Toast.makeText(rootView.getContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Endereco endereco = response.body();

                    estabelecimentoComercial.setLocalizacao(endereco.getId());
                    updateEstabelecimentoComercialCall(mApiService, estabelecimentoComercial.getId(), estabelecimentoComercial);

                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                call.cancel();
                Toast.makeText(rootView.getContext(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateEstabelecimentoComercialCall(ApiInterface mApiService, Integer idEstabelecimentoComercial, EstabelecimentoComercial estabelecimentoComercial) {
        Call<EstabelecimentoComercial> mService = mApiService.estabelecimentoUpdate(idEstabelecimentoComercial, estabelecimentoComercial);
        mService.enqueue(new Callback<EstabelecimentoComercial>() {
            @Override
            public void onResponse(Call<EstabelecimentoComercial> call, Response<EstabelecimentoComercial> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(rootView.getContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    EstabelecimentoComercial mEstabelecimentoComercialObject = response.body();
                    Toast.makeText(rootView.getContext(), "Estabelecimento alterado!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getEstabelecimentoComercial(Integer idEstabelecimentoComercial) {
        try {
            ApiInterface mApiService = this.getInterfaceService();
            Call<EstabelecimentoComercialEndereco> mService = mApiService.getEstabelecimentoComercialEndereco(idEstabelecimentoComercial);
            mService.enqueue(new Callback<EstabelecimentoComercialEndereco>() {
                @Override
                public void onResponse(Call<EstabelecimentoComercialEndereco> call, Response<EstabelecimentoComercialEndereco> response) {
                    int statusCode;

                    if(!response.isSuccessful()){
                        //Log.d("Response Failed", response.message());
                        Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                    } else {
                        //Log.d("Response Worked", response.message());
                        statusCode = response.code();
                        //Log.d("StatusCode", Integer.toString(statusCode));

                        EstabelecimentoComercialEndereco estabelecimentoComercialEndereco = response.body();

                        nomeEdTex.setText(estabelecimentoComercialEndereco.getNome());
                        cnpjEdTex.setText(estabelecimentoComercialEndereco.getCnpj());
                        telefoneEdTex.setText(estabelecimentoComercialEndereco.getTelefoneCom());
                        cepEdTex.setText(estabelecimentoComercialEndereco.getLocalizacao().getCep());
                        logradouroEdTex.setText(estabelecimentoComercialEndereco.getLocalizacao().getLogradouro());
                        bairroEdTex.setText(estabelecimentoComercialEndereco.getLocalizacao().getBairro());
                        numeroEdTex.setText(estabelecimentoComercialEndereco.getLocalizacao().getNumero());
                        categoriaEdTex.setText(estabelecimentoComercialEndereco.getCategoria().getNome());

                        categoria = estabelecimentoComercialEndereco.getCategoria().getId();
                    }
                }

                @Override
                public void onFailure(Call<EstabelecimentoComercialEndereco> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Log.d("getEstabelecimento", e.toString());
        }
    }
}
