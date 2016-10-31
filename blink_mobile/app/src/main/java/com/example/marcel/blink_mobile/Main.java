package com.example.marcel.blink_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcel.blink_mobile.interfaces.ApiInterface;
import com.example.marcel.blink_mobile.models.UserData;
import com.example.marcel.blink_mobile.models.Usuario;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends ActionBarActivity {
    final String LOGIN_URL = "http://blink-brunopansani-1.c9users.io/";

    View focusView = null;
    private EditText login;
    private EditText pass;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
				switch (v.getId()) {
					case R.id.btnContinuar:
						login = (EditText) findViewById(R.id.txt_main_login);
						pass = (EditText) findViewById(R.id.txt_main_senha);

						email = login.getText().toString().trim();
						senha = pass.getText().toString().trim();

						attemptLogin();
                        break;
					 case R.id.btnCadastrarCliente:
						CreateNovoCliente();
						break;
                    case R.id.btnCadastrarVendedor:
                        CreateNovoVendedor();
                        break;
					default:
						break;
				}
                
            }
        };

        Button btnContinuar = (Button) findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(listener);
		
		Button btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);
        btnCadastrarCliente.setOnClickListener(listener);

        Button btnCadastrarVendedor = (Button) findViewById(R.id.btnCadastrarVendedor);
        btnCadastrarVendedor.setOnClickListener(listener);
    }

    private void attemptLogin(){
        boolean mCancel = this.loginValidation(email, senha);
        if (mCancel) {
            focusView.requestFocus();
        } else {
            loginProcessWithRetrofit(email, senha);
        }
    }
	
	public void CreateNovoCliente(){
        Intent intent = new Intent (Main.this, CadastroCliente.class);
        startActivity(intent);
    };

    public void CreateNovoVendedor(){
        Intent intent = new Intent (Main.this, CadastroVendedor.class);
        startActivity(intent);
    };

    private boolean loginValidation(String email, String senha) {
        // Reset errors.
        login.setError(null);
        pass.setError(null);

        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(senha) && !isPasswordValid(senha)) {
            pass.setError("Senha Inválida");
            focusView = pass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            login.setError("E-mail não preenchido.");
            focusView = login;
            cancel = true;
        } else if (!isEmailValid(email)) {
            login.setError("E-mail inválido");
            focusView = login;
            cancel = true;
        }
        return cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                .baseUrl(LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

    private void loginProcessWithRetrofit(final String email, String senha){
        ApiInterface mApiService = this.getInterfaceService();
        Call<Usuario> mService = mApiService.authenticate(email, senha);
        mService.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int statusCode;

                if(!response.isSuccessful()){
                    Log.d("Response Failed", response.message());
                    Toast.makeText(getApplicationContext(), "Login e/ou senha incorreto(s)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Response Worked", response.message());
                    statusCode = response.code();
                    Log.d("StatusCode", Integer.toString(statusCode));

                    Usuario mUsuarioObject = response.body();
                    UserData mUserDataObject;
                    String returnedResponse = null;

                    if(mUsuarioObject != null) {
                        try {
                            mUserDataObject = mUsuarioObject.getUserData();

                            if(mUserDataObject != null){
                                returnedResponse = mUserDataObject.toString();
                                Log.d("Response String", returnedResponse);
                            } else {
                                Log.d("Response Error", "UserData is null");
                            }

                        } catch(Exception e) {
                            Log.d("Response Error", e.toString());
                        }
                    } else {
                        Log.d("Response Error", "Usuario is null");
                    }


                    if(returnedResponse == null) {
                        Log.d("Response Error", "Deu MUITO ruim.");
                        Toast.makeText(Main.this, "Aconteceu algo inesperado.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(Main.this, Drawer.class);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                call.cancel();
                Toast.makeText(Main.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }
}

