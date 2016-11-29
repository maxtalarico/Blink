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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

public class DadosCliente extends Fragment /*implements View.OnClickListener*/ {
    public static final int REQUEST_CODE = 0;
    final String REGISTER_URL = "http://blink-brunopansani-1.c9users.io/";

    private View rootView;

    EditText emailEdTex;
    EditText emailConfEdTex;

    EditText senhaEdTex;
    EditText senhaConfEdTex;

    EditText nomeEdTex;
    EditText dataNascimentoEdTex;
    EditText cpfEdTex;
    EditText telResEdTex;
    EditText celularEdTex;
    EditText telComEdTex;

    EditText cepEdTex;
    Spinner estadoSpinner;
    Spinner cidadeSpinner;
    EditText logradouroEdTex;
    EditText bairroEdTex;
    EditText numeroEdTex;

    String email;
    String emailConf;

    String senha;
    String senhaConf;

    String nome;
    String dataNascimento;
    String cpf;
    String telRes;
    String celular;
    String telCom;

    String cep;
    int estado;
    int  cidade;
    String logradouro;
    String bairro;
    String numero;

    Activity activity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = getActivity();

        rootView = inflater.inflate(R.layout.fragment_dados_cliente, container, false);

        emailEdTex = (EditText) rootView.findViewById(R.id.campoEmail);
        //emailConfEdTex = (EditText) rootView.findViewById(R.id.campoEmailConf);
        senhaEdTex = (EditText) rootView.findViewById(R.id.txt_senha);
        senhaConfEdTex = (EditText) rootView.findViewById(R.id.txt_confirmar_senha);
        nomeEdTex = (EditText) rootView.findViewById(R.id.campoNome);
        dataNascimentoEdTex = (EditText) rootView.findViewById(R.id.campoDataNasc);
        cpfEdTex = (EditText) rootView.findViewById(R.id.campoCPF);
        telResEdTex = (EditText) rootView.findViewById(R.id.campoTelefone);
        celularEdTex = (EditText) rootView.findViewById(R.id.campoCelular);
        cepEdTex = (EditText) rootView.findViewById(R.id.campoCEP);
                       /* estadoSpinner = (Spinner) rootView.findViewById(R.id.spn_estado);
                        cidadeSpinner = (Spinner) rootView.findViewById(R.id.spn_cidade);*/
        logradouroEdTex = (EditText) rootView.findViewById(R.id.campoEndereco);
        bairroEdTex = (EditText) rootView.findViewById(R.id.campoBairro);
        numeroEdTex = (EditText) rootView.findViewById(R.id.campoNo);

        getUsuario(null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_alterar:

                        emailEdTex = (EditText) rootView.findViewById(R.id.campoEmail);
                        //emailConfEdTex = (EditText) rootView.findViewById(R.id.campoEmailConf);
                        senhaEdTex = (EditText) rootView.findViewById(R.id.txt_senha);
                        senhaConfEdTex = (EditText) rootView.findViewById(R.id.txt_confirmar_senha);
                        nomeEdTex = (EditText) rootView.findViewById(R.id.campoNome);
                        dataNascimentoEdTex = (EditText) rootView.findViewById(R.id.campoDataNasc);
                        cpfEdTex = (EditText) rootView.findViewById(R.id.campoCPF);
                        telResEdTex = (EditText) rootView.findViewById(R.id.campoTelefone);
                        celularEdTex = (EditText) rootView.findViewById(R.id.campoCelular);
                        cepEdTex = (EditText) rootView.findViewById(R.id.campoCEP);
                       /* estadoSpinner = (Spinner) rootView.findViewById(R.id.spn_estado);
                        cidadeSpinner = (Spinner) rootView.findViewById(R.id.spn_cidade);*/
                        logradouroEdTex = (EditText) rootView.findViewById(R.id.campoEndereco);
                        bairroEdTex = (EditText) rootView.findViewById(R.id.campoBairro);
                        numeroEdTex = (EditText) rootView.findViewById(R.id.campoNo);

                        if ("".equals(telResEdTex.getText().toString().trim())) {
                            telResEdTex.setError("Campo obrigatório");
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
                        registerAttemptWithRetrofit(emailEdTex.getText().toString(),
                                                    //emailConfEdTex.getText().toString(),
                                                    //senha,
                                                    //senhaConf,
                                                    nomeEdTex.getText().toString(),
                                                    dataNascimentoEdTex.getText().toString(),
                                                    cpfEdTex.getText().toString(),
                                                    telResEdTex.getText().toString(),
                                                    celularEdTex.getText().toString(),
                                                    cepEdTex.getText().toString(),
                                                    estado,
                                                    cidade,
                                                    logradouroEdTex.getText().toString(),
                                                    bairroEdTex.getText().toString(),
                                                    numeroEdTex.getText().toString());

                        fragment = new DadosCliente();
                    }
                        break;

                    case R.id.btn_cancelar:
                        fragment = new DadosCliente();
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

/*
    public void onClick(View v) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.btn_alterar:

                registerAttemptWithRetrofit();
                break;

            case R.id.btn_cancelar:

                fragment = new ClienteHome();
                break;

            default:
                break;
        }

        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
*/

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

    private void registerAttemptWithRetrofit(String email,
                                              //String emailConf,
                                              //String senha,
                                              //String senhaConf,
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
                                              String numero){
        /*email = "cliente@teste.com";
        emailConf = "cliente@teste.com";
        senha = "123456";
        senhaConf = "123456";
        nome = "Cliente Teste";
        dataNascimento = "12/11/1993";
        cpf = "423.370.988.00";
        telRes = "(19) 3852-0000";
        celular = "(19) 3852-11111";
        cep = "13920-000";
        logradouro = "Rua TesteX";
        bairro = "Bairro Teste";
        numero = "22";*/

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuarioAtivo = (Usuario) b.getSerializable("Usuario");
        UserData userDataAtivo = usuarioAtivo.getUserData();
        Cliente clienteAtivo = userDataAtivo.getCliente();
        Endereco enderecoAtivo = userDataAtivo.getEndereco();

        Vendedor vendedor = null;

        /*DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = format.parse();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        Cliente cliente = new Cliente(clienteAtivo.getId(), celular, clienteAtivo.getCpf(), clienteAtivo.getDataNascimento(), clienteAtivo.getNome(), telRes);
        Endereco endereco = new Endereco(enderecoAtivo.getId(), bairro, cidade, estado, logradouro, numero, cep);

        Usuario usuario = new Usuario(userDataAtivo.getId(), vendedor, cliente, endereco, userDataAtivo.getEmail(), userDataAtivo.getSenha(), userDataAtivo.getNome());
        UserData userData = usuario.getUserData();

        //usuarioAtivo = usuario;

        //i.putExtra("Usuario", usuarioAtivo);

        ApiInterface mApiService = this.getInterfaceService();

        updateClienteCall(mApiService, userData);
    }

    private void updateClienteCall(final ApiInterface mApiService, final UserData userData) {
        Cliente cliente = userData.getCliente();

        Call<Cliente> mServiceCliente = mApiService.clienteUpdate(cliente.getId(), cliente);
        mServiceCliente.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(rootView.getContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Cliente cliente = response.body();

                    updateEnderecoCall(mApiService, userData);
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                call.cancel();
                Toast.makeText(rootView.getContext(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateEnderecoCall(final ApiInterface mApiService, final UserData userData) {
        Endereco endereco = userData.getEndereco();
        Call<Endereco> mServiceEndereco = mApiService.enderecoUpdate(endereco.getId(), endereco);
        mServiceEndereco.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(rootView.getContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Endereco endereco = response.body();

                    updateUsuarioCall(mApiService, userData);
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                call.cancel();
                Toast.makeText(rootView.getContext(), "Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUsuarioCall(ApiInterface mApiService, UserData userData) {
        userData.setCliente(null);
        userData.setEndereco(null);
        Call<UserData> mServiceUsuario = mApiService.userUpdate(userData.getId(), userData);
        mServiceUsuario.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    //Toast.makeText(rootView.getContext(), "Tente novamente. Usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(rootView.getContext(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    UserData userData = response.body();
                    Log.d("getUsuario", userData.toString());
                    getUsuario(userData);
                    Toast.makeText(rootView.getContext(), "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();

                    //getUsuario(usuario.getUserData());
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                call.cancel();
                Toast.makeText(rootView.getContext(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUsuario(UserData userData) {
        try {
            Intent i = activity.getIntent();
            Bundle b = i.getExtras();

            Usuario usuario = (Usuario) b.getSerializable("Usuario");

            if(userData == null) {
                userData = usuario.getUserData();
                Log.d("getUsuario", "Usuario null");
            } else {
                usuario.setUserData(userData);
                i.putExtra("Usuario", usuario);
                Log.d("getUsuario", "Novo usuario na activity");
            }

            if(userData == null)
                Log.d("getUsuario", "UserData Null");

            Cliente cliente = userData.getCliente();
            Endereco endereco = userData.getEndereco();

            emailEdTex.setText(userData.getEmail());

            //EditText senhaEdTex;
            //EditText senhaConfEdTex;

            nomeEdTex.setText(userData.getNome());

            DateFormat date = new SimpleDateFormat("dd//MM/yyyy");
            dataNascimentoEdTex.setText(date.format(cliente.getDataNascimento()));

            cpfEdTex.setText(cliente.getCpf());
            telResEdTex.setText(cliente.getTelefoneRes());
            celularEdTex.setText(cliente.getCelular());

            cepEdTex.setText(endereco.getCep());
            //estadoSpinner;
            //cidadeSpinner;
            logradouroEdTex.setText(endereco.getLogradouro());
            bairroEdTex.setText(endereco.getBairro());
            numeroEdTex.setText(endereco.getNumero());

        } catch (Exception e) {
            Log.d("getUsuario", e.toString());
        }
    }
}
