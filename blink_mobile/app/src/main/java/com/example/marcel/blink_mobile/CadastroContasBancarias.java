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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.ContaBancaria;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Vendedor;

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

public class CadastroContasBancarias extends Fragment  {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    String banco;
    String tipoConta;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cadastro_contas_bancarias, container, false);

        //Spinner Categoria

        ArrayList<String> bancos = getBancos("bancos.json");
        final Spinner spinnerBancos=(Spinner) view.findViewById(R.id.spn_banco);
        ArrayAdapter<String> adapterBancos = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, bancos);
        spinnerBancos.setAdapter(adapterBancos);
        banco = spinnerBancos.getSelectedItem().toString();

        ArrayList<String> contas = getTiposConta("contas.json");
        final Spinner SpinnerContas=(Spinner) view.findViewById(R.id.spn_tipo_de_conta);
        ArrayAdapter<String> adapterContas = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, contas);
        SpinnerContas.setAdapter(adapterContas);
        tipoConta = spinnerBancos.getSelectedItem().toString();

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_cadastrar_conta:

                        EditText agencia = (EditText) getView().findViewById(R.id.txt_agencia);
                        EditText DVA = (EditText) getView().findViewById(R.id.txt_digito_agencia);
                        EditText conta = (EditText) getView().findViewById(R.id.txt_conta);
                        EditText DVC = (EditText) getView().findViewById(R.id.txt_digito_conta);

                        if ("".equals(agencia.getText().toString().trim())) {
                            agencia.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Nome do Dispositivo é Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ("".equals(DVA.getText().toString().trim())) {
                            DVA.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Digito da Agência Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ("".equals(conta.getText().toString().trim())) {
                            conta.setError("Campo obrigatório");
                            //Toast.makeText(getActivity(), "Campo Conta Obrigatório", Toast.LENGTH_SHORT).show();
                            return;
                        }if ("".equals(DVC.getText().toString().trim())) {
                        DVC.setError("Campo obrigatório");
                        // Toast.makeText(getActivity(), "Campo Digito da Conta Obrigatório", Toast.LENGTH_SHORT).show();
                        return;
                        } else {
                            registerAttemptWithRetrofit(Integer.parseInt(banco.split(" - ")[0]),
                                    agencia.getText().toString(),
                                    DVA.getText().toString(),
                                    conta.getText().toString(),
                                    DVC.getText().toString(),
                                    Integer.parseInt(tipoConta.split(" - ")[0]));

                            fragment = new ContasBancarias();
                        }
                        break;

                    default:
                        break;
                }



                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) view.findViewById(R.id.btn_cadastrar_conta);
        button.setOnClickListener(listener);

        EditText agencia = (EditText) view.findViewById(R.id.txt_agencia);
        MaskEditTextChangedListener maskAgencia = new MaskEditTextChangedListener("####", agencia);
        EditText DVA = (EditText) view.findViewById(R.id.txt_digito_agencia);
        MaskEditTextChangedListener maskDVA = new MaskEditTextChangedListener("#", DVA);
        EditText conta = (EditText) view.findViewById(R.id.txt_conta);
        MaskEditTextChangedListener maskConta = new MaskEditTextChangedListener("#######", conta);
        EditText DVC = (EditText) view.findViewById(R.id.txt_digito_conta);
        MaskEditTextChangedListener maskDVC = new MaskEditTextChangedListener("#", DVC);

        agencia.addTextChangedListener(maskAgencia);
        DVA.addTextChangedListener(maskDVA);
        conta.addTextChangedListener(maskConta);
        DVC.addTextChangedListener(maskDVC);

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

    private void registerAttemptWithRetrofit( Integer banco,
                                              String agencia,
                                              String digitoAgencia,
                                              String numeroConta,
                                              String digitoConta,
                                              Integer tipoConta){

        /*banco = 1;
        agencia = "3948";
        digitoAgencia = "0";
        numeroConta = "01089847";
        digitoConta = "3";
        tipoConta = 1;*/

        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));

        Integer id = null;

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        List<ContaBancaria> contasList = new ArrayList<ContaBancaria>();
        ContaBancaria conta = new ContaBancaria(agencia, banco, digitoAgencia, digitoConta, id, numeroConta, tipoConta, vendedor.getId());
        contasList.add(conta);

        ContaBancaria[] contas = new ContaBancaria[contasList.size()];
        contas = contasList.toArray(contas);

        vendedor.setContasBancarias(contas);

        Log.d("UsuarioCliente", userData.toString());

        ApiInterface mApiService = this.getInterfaceService();
        Call<ContaBancaria> mService = mApiService.contaBancariaCreate(conta);
        mService.enqueue(new Callback<ContaBancaria>() {
            @Override
            public void onResponse(Call<ContaBancaria> call, Response<ContaBancaria> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    ContaBancaria mContaBancariaObject = response.body();


                }
            }

            @Override
            public void onFailure(Call<ContaBancaria> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<String> getBancos(String jsonFile) {
        JSONArray jsonArray = null;
        ArrayList<String> bancosList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(jsonFile);
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");

            JSONObject object = new JSONObject(json);
            jsonArray  = object.getJSONArray("bancos");

            for (int i = 0; i < jsonArray.length(); i++) {
                bancosList.add(jsonArray.getJSONObject(i).getInt("id") + " - " + jsonArray.getJSONObject(i).getString("nome"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return bancosList;
    }

    public ArrayList<String> getTiposConta(String jsonFile) {
        JSONArray jsonArray = null;
        ArrayList<String> tiposContaList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(jsonFile);
            int size=is.available();
            byte[] data=new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");

            JSONObject object = new JSONObject(json);
            jsonArray  = object.getJSONArray("contas");

            for (int i = 0; i < jsonArray.length(); i++) {
                tiposContaList.add(jsonArray.getJSONObject(i).getInt("id") + " - " + jsonArray.getJSONObject(i).getString("nome"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return tiposContaList;
    }
}
