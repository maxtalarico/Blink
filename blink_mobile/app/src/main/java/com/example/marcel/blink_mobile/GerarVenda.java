package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.Aparelho;
import com.example.marcel.blink_mobile.models.Compra;
import com.example.marcel.blink_mobile.models.EstabelecimentoComercial;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;
import com.example.marcel.blink_mobile.models.Venda;
import com.example.marcel.blink_mobile.models.Vendedor;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.encode.MyEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

public class GerarVenda extends Fragment {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    EditText etValor;
    ImageView qrImage;
    Spinner estabelecimentoSpinner;
    String valor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gerar_venda, container, false);

        etValor = (EditText) rootView.findViewById(R.id.editText);
        valor = etValor.getText().toString();

        qrImage = (ImageView ) rootView.findViewById(R.id.imageView);

        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        Usuario usuario = (Usuario) b.getSerializable("Usuario");
        UserData userData = usuario.getUserData();
        Vendedor vendedor = userData.getVendedor();

        setEstabelecimentosSpinner(vendedor.getId());

        Button gerar = (Button) rootView.findViewById(R.id.button);
        gerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = etValor.getText().toString();

                registerAttemptWithRetrofit(valor);
            }
        });

        Button cancelar = (Button) rootView.findViewById(R.id.button2);
        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new VendedorHome();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(fragment != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            }
        });

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

    private void registerAttemptWithRetrofit(String valor) {
        Activity activity = getActivity();
        Intent i = activity.getIntent();
        Bundle b = i.getExtras();

        final Integer idEstabelecimentoComercial = b.getInt("idEstabelecimentoComercial");
        Log.d("id in aparelho", Integer.toString(idEstabelecimentoComercial));

        valor = "12.50";

        ApiInterface mApiService = this.getInterfaceService();
        Call<Aparelho[]> mService = mApiService.getAparelho(idEstabelecimentoComercial);
        final String finalValor = valor;
        mService.enqueue(new Callback<Aparelho[]>() {
            @Override
            public void onResponse(Call<Aparelho[]> call, Response<Aparelho[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    Aparelho[] aparelhos = response.body();
                    boolean found = false;

                    int qtdAparelhos = Array.getLength(aparelhos);
                    for (int x = 0; x < qtdAparelhos; x++ ) {
                        Log.d("Aparelhos Serial", "idAparelhos: " + aparelhos[x].getSerial() + "idAtual: " + Build.SERIAL);
                        if(aparelhos[x].getSerial().equals(Build.SERIAL)) {
                            createVenda(finalValor, idEstabelecimentoComercial, aparelhos[x].getId());
                            found = true;
                        }
                        Log.d("Cartao: ", aparelhos[x].toString());
                    }

                    if(!found)
                        Toast.makeText(getActivity(), "Este aparelho não tem permissão para vender", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aparelho[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createVenda(String finalValor, Integer idEstabelecimentoComercial, Integer idAparelho) {
        Date data = new Date();

        Float valorTotal = Float.parseFloat(finalValor);

        Venda venda = new Venda(idAparelho, idEstabelecimentoComercial, valorTotal);

        ApiInterface mApiService = this.getInterfaceService();
        Call<Compra> mService = mApiService.compraCreate(venda);
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

                    Compra compra = response.body();
                    try {
                        generateQrCode(compra.getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Compra> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void generateQrCode(Integer idCompra) throws InterruptedException {
        String contents = Integer.toString(idCompra);
        int pixelResolution = 150;
        BarcodeFormat format = BarcodeFormat.QR_CODE;

        MyEncoder encoder = new MyEncoder(contents, pixelResolution, format);
        Bitmap bitmap = encoder.getBitmap();

        if(bitmap != null) {
            qrImage.setImageBitmap(bitmap);
        }

        ApiInterface mApiService = this.getInterfaceService();

        Runnable r = new MyThread(idCompra, mApiService);
        Thread t = new Thread(r);
        //new Thread(r).start();
        t.start();
    }

    public void setEstabelecimentosSpinner(Integer idVendedor) {
        //Log.d("IDs", "registerAttemptWithRetrofit: estado: " + Integer.toString(estado) + " | cidade: " + Integer.toString(cidade));
        ApiInterface mApiService = this.getInterfaceService();
        Call<EstabelecimentoComercial[]> mService = mApiService.getEstabelecimentos(idVendedor);
        mService.enqueue(new Callback<EstabelecimentoComercial[]>() {
            @Override
            public void onResponse(Call<EstabelecimentoComercial[]> call, Response<EstabelecimentoComercial[]> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    //Log.d("Response Failed", response.message());
                    Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    //Log.d("StatusCode", Integer.toString(statusCode));

                    EstabelecimentoComercial[] estabelecimentoComercials = response.body();

                    Activity activity = getActivity();
                    Intent i = activity.getIntent();
                    Bundle b = i.getExtras();

                    Usuario usuario = (Usuario) b.getSerializable("Usuario");
                    UserData userData = usuario.getUserData();
                    Vendedor vendedor = userData.getVendedor();

                    vendedor.setEstabelecimentoComercials(estabelecimentoComercials);

                    getEstabelecimentosArrayList(estabelecimentoComercials, vendedor);
                }
            }

            @Override
            public void onFailure(Call<EstabelecimentoComercial[]> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getEstabelecimentosArrayList(EstabelecimentoComercial[] estabelecimentoComercials, Vendedor vendedor) {
        View view = getView();

        try {
            int qtdContas = Array.getLength(estabelecimentoComercials);

            Integer idEstabelecimento;
            String nomeEstabelecimento;

            ArrayList<String> estabelecimentosStrings = new ArrayList<String>();

            for (int x = 0; x < qtdContas; x++ ) {
                idEstabelecimento = estabelecimentoComercials[x].getId();
                nomeEstabelecimento = estabelecimentoComercials[x].getNome();

                estabelecimentosStrings.add(Integer.toString(idEstabelecimento) + " - " + nomeEstabelecimento);
            }

            Activity activity = getActivity();
            Intent i = activity.getIntent();

            estabelecimentoSpinner = (Spinner) view.findViewById(R.id.spinnerEstabelecimento);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.textView, estabelecimentosStrings);
            estabelecimentoSpinner.setAdapter(adapter);

            String estabelecimentoSpinnerString = estabelecimentoSpinner.getSelectedItem().toString();
            Integer idEstabelecimentoComercial = Integer.parseInt(estabelecimentoSpinnerString.split(" - ")[0]);
            i.putExtra("idEstabelecimentoComercial", idEstabelecimentoComercial);
            registerAttemptWithRetrofit(valor);

            estabelecimentoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Activity activity = getActivity();
                    Intent i = activity.getIntent();

                    String estabelecimentoSpinnerString = estabelecimentoSpinner.getSelectedItem().toString();
                    Integer idEstabelecimentoComercial = Integer.parseInt(estabelecimentoSpinnerString.split(" - ")[0]);
                    i.putExtra("idEstabelecimentoComercial", idEstabelecimentoComercial);
                    Log.d("id", "onItemSelected: " + estabelecimentoSpinnerString.split(" - ")[0]);
                    registerAttemptWithRetrofit(valor);
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

    public class MyThread implements Runnable {
        Integer idCompra;
        ApiInterface mApiService;

        public MyThread(Integer idCompra, ApiInterface mApiService) {
            this.idCompra = idCompra;
            this.mApiService = mApiService;
        }

        public void run() {
                final boolean[] stateDefined = {false};
                for (int tries = 0; tries < 30; tries++) {
                    //ApiInterface mApiService = this.getInterfaceService();
                    Call<Compra> mService = mApiService.getCompra(idCompra);
                    final int finalTries = tries;

                    if (stateDefined[0]) {
                        break;
                    }
                    mService.enqueue(new Callback<Compra>() {
                        @Override
                        public void onResponse(Call<Compra> call, Response<Compra> response) {
                            int statusCode;

                            if (!response.isSuccessful()) {
                                //Log.d("Response Failed", response.message());
                                Toast.makeText(getActivity(), "Tente novamente.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Log.d("Response Worked", response.message());
                                statusCode = response.code();
                                //Log.d("StatusCode", Integer.toString(statusCode));

                                Compra compra = response.body();

                                android.app.FragmentManager fm = getActivity().getFragmentManager();
                                ResultadoCompra resultadoCompra = new ResultadoCompra();
                                Bundle b = new Bundle();

                                if (compra.getStatus().equals("Autorizada")) {
                                    b.putString("resultado", "Autorizada");
                                    resultadoCompra.setArguments(b);
                                    resultadoCompra.show(fm, "Compra #" + compra.getId());
                                    stateDefined[0] = true;

                                } else if (compra.getStatus().equals("Cancelada")) {
                                    b.putString("resultado", "Cancelada");
                                    resultadoCompra.setArguments(b);
                                    resultadoCompra.show(fm, "Compra #" + compra.getId());
                                    stateDefined[0] = true;

                                } else if (finalTries == 29) {
                                    b.putString("resultado", "Cancelar");
                                    resultadoCompra.setArguments(b);
                                    resultadoCompra.show(fm, "Compra #" + compra.getId());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Compra> call, Throwable t) {
                            call.cancel();
                            Toast.makeText(getActivity(), "Não foi possível encontrar conexão com a internet", Toast.LENGTH_LONG).show();
                        }
                    });
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
