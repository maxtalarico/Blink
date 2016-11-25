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
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.Endereco;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

public class DadosVendedor extends Fragment implements View.OnClickListener {
    public static final int REQUEST_CODE = 0;
    final String REGISTER_URL = "http://blink-brunopansani-1.c9users.io/";

    private View rootView;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dados_vendedor, container, false);

        getUsuario(null);

        Button btnAlterar = (Button)rootView.findViewById(R.id.btn_alterar);
        btnAlterar.setOnClickListener(this);

        Button btnCancelar= (Button)rootView.findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(this);

        return rootView;
    }

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
                fragment = new VendedorHome();
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
        email = "vendor99@teste.com";
        emailConf = "vendor99@teste.com";
        senha = "123456";
        senhaConf = "123456";
        nome = "Vendor Teste";
        dataNascimento = "12/11/1993";
        cpf = "423.370.988.00";
        telRes = "(19) 3852-9999";
        celular = "(19) 3852-77777";
        telCom = "(19) 3852-1111";
        cep = "13920-333";
        logradouro = "Rua TesteZ";
        bairro = "Bairro TesteA";
        numero = "33";

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = format.parse(dataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuarioAtivo = (Usuario) b.getSerializable("Usuario");
        UserData userDataAtivo = usuarioAtivo.getUserData();
        Vendedor vendedorAtivo = userDataAtivo.getVendedor();
        Endereco enderecoAtivo = userDataAtivo.getEndereco();

        Integer id = userDataAtivo.getId();

        Vendedor vendedor = new Vendedor(vendedorAtivo.getId(), celular, cpf, data, nome, telCom, telRes);
        Cliente cliente = null;
        Endereco endereco = new Endereco(enderecoAtivo.getId(), bairro, cidade, estado, logradouro, numero, cep);

        Usuario usuario = new Usuario(userDataAtivo.getId(), vendedor, cliente, endereco, email, senha, nome);
        UserData userData = usuario.getUserData();

        //usuarioAtivo = usuario;

        //i.putExtra("Usuario", usuarioAtivo);

        ApiInterface mApiService = this.getInterfaceService();

        updateVendedorCall(mApiService, userData);
    }

    private void updateVendedorCall(final ApiInterface mApiService, final UserData userData) {
        Vendedor vendedor = userData.getVendedor();

        Call<Vendedor> mServiceVendedor = mApiService.vendedorUpdate(vendedor.getId(), vendedor);
        mServiceVendedor.enqueue(new Callback<Vendedor>() {
            @Override
            public void onResponse(Call<Vendedor> call, Response<Vendedor> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Vendedor vendedor = response.body();

                    updateEnderecoCall(mApiService, userData);
                }
            }

            @Override
            public void onFailure(Call<Vendedor> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUsuarioCall(ApiInterface mApiService, UserData userData) {
        userData.setVendedor(null);
        userData.setEndereco(null);
        Call<UserData> mServiceUsuario = mApiService.userUpdate(userData.getId(), userData);
        mServiceUsuario.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    //Toast.makeText(getActivity(), "Tente novamente. Usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    UserData userData = response.body();
                    getUsuario(userData);
                    Toast.makeText(getActivity(), "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();

                    //getUsuario(usuario.getUserData());
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUsuario(UserData userData) {
        try {
            Activity activity = getActivity();
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

            TextView tvTeste = (TextView)rootView.findViewById(R.id.teste);
            tvTeste.setText(userData.toString());
            Log.d("UserData", userData.toString());

        } catch (Exception e) {
            Log.d("getUsuario", e.toString());
        }
    }
}
