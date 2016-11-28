package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Autorizacao;
import com.example.marcel.blink_mobile.models.Cartao;
import com.example.marcel.blink_mobile.models.Cliente;
import com.example.marcel.blink_mobile.models.Compra;
import com.example.marcel.blink_mobile.models.Compras;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcel on 26/11/2016.
 */

public class ConfirmacaoCompra extends DialogFragment {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";
    View rootView;

    Spinner cartoesSpinner;

    private DialogInterface.OnDismissListener onDismissListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_confirmacao_compra, container, false);
        getDialog().setTitle("Simple Dialog");

        Bundle b = getArguments();
        String idCompraString = b.getString("idCompra");
        Integer idCompra = Integer.parseInt(idCompraString);

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b2 = i.getExtras();

        Usuario usuario = (Usuario) b2.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Cliente cliente = userData.getCliente();

        setCartoesSpinner(cliente.getId());

        getCompra(idCompra);

        return rootView;
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

    public void getCompra(Integer idCompra) {
        ApiInterface mApiService = this.getInterfaceService();
        Call<Compras> mService = mApiService.getCompras(idCompra);
        mService.enqueue(new Callback<Compras>() {
            @Override
            public void onResponse(Call<Compras> call, Response<Compras> response) {
                int statusCode;

                if (!response.isSuccessful()) {
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Compras compra = response.body();

                    generateButtons(compra);

                    TextView tvEstabelecimento = (TextView) rootView.findViewById(R.id.txt_nome_estabelecimento);
                    tvEstabelecimento.setText(compra.getEstabelecimento().getNome());

                    TextView tvValor = (TextView) rootView.findViewById(R.id.txt_valor);
                    tvValor.setText("R$" + Float.toString(compra.getValorTotal()));
                }
            }

            @Override
            public void onFailure(Call<Compras> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void generateButtons(final Compras compra) {
        final Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Cliente cliente = userData.getCliente();
        final Integer idCartaoFinal = b.getInt("idCartaoFinal");


        final Integer idCompra = compra.getId();
        final Integer idCartao = idCartaoFinal;
        final Integer idCliente = cliente.getId();

        Button confirm = (Button) rootView.findViewById(R.id.btn_confirmar);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fecharCompra("Autorizar", idCartao, idCliente, idCompra);
            }
        });

        Button dismiss = (Button) rootView.findViewById(R.id.btn_cancelar);

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fecharCompra("Cancelar", idCartao, idCliente, idCompra);
            }
        });

    }

    public void fecharCompra(String status, Integer idCartao, Integer idCliente, Integer idCompra) {
        Autorizacao autorizacao = new Autorizacao(status, idCartao, idCliente, idCompra);

        ApiInterface mApiService = this.getInterfaceService();
        Call<Compra> mService = mApiService.fecharCompra(autorizacao);
        mService.enqueue(new Callback<Compra>() {
            @Override
            public void onResponse(Call<Compra> call, Response<Compra> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<Compra> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    public void setCartoesSpinner(Integer idCliente) {
        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));
        ApiInterface mApiService = this.getInterfaceService();
        Call<Cartao[]> mService = mApiService.getCartao(idCliente);
        mService.enqueue(new Callback<Cartao[]>() {
            @Override
            public void onResponse(Call<Cartao[]> call, Response<Cartao[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Cartao[] cartoes = response.body();

                    Activity activity = getActivity();
                    Intent i = activity.getIntent();
                    Bundle b = i.getExtras();

                    Usuario usuario = (Usuario) b.getSerializable("Usuario");
                    UserData userData = usuario.getUserData();
                    Cliente cliente = userData.getCliente();

                    cliente.setCartoes(cartoes);

                    getEstabelecimentosArrayList(cartoes);
                }
            }

            @Override
            public void onFailure(Call<Cartao[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getEstabelecimentosArrayList(Cartao[] cartoes) {
        View view = getView();

        Log.d("Sem Estabelecimentos", "here");

        try {
            int qtdCartoes = Array.getLength(cartoes);

            String nomeBandeira;
            String numeroCartao;
            Integer idCartao;

            ArrayList<String> cartoesStrings = new ArrayList<String>();

            for (int x = 0; x < qtdCartoes; x++ ) {
                idCartao = cartoes[x].getId();
                nomeBandeira = cartoes[x].getBandeira();
                numeroCartao = cartoes[x].getNumero();

                cartoesStrings.add(Integer.toString(idCartao) + " - " + nomeBandeira + " / " + numeroCartao);
            }

            Activity activity = getActivity();
            Intent i = activity.getIntent();

            cartoesSpinner = (Spinner) view.findViewById(R.id.spn_cartao);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, cartoesStrings);
            cartoesSpinner.setAdapter(adapter);

            String cartoesSpinnerString = cartoesSpinner.getSelectedItem().toString();
            Integer idCartaoFinal = Integer.parseInt(cartoesSpinnerString.split(" - ")[0]);
            i.putExtra("idCartaoFinal", idCartaoFinal);

            cartoesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Activity activity = getActivity();
                    Intent i = activity.getIntent();

                    String cartoesSpinnerString = cartoesSpinner.getSelectedItem().toString();
                    Integer idCartaoFinal = Integer.parseInt(cartoesSpinnerString.split(" - ")[0]);
                    i.putExtra("idCartaoFinal", idCartaoFinal);
                    Log.d("id", "onItemSelected: " + cartoesSpinnerString.split(" - ")[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        } catch (Exception e) {
            Log.d("Sem Estabelecimentos", e.toString());
        }
    }
}
