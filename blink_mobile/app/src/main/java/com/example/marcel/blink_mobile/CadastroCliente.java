package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class CadastroCliente extends ActionBarActivity {
//implements View.OnClickListener

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        ArrayList<String> estados = getEstados("estados-cidades.json");
        final Spinner spinnerEstados=(Spinner) this.findViewById(R.id.spinnerEstado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, estados);
        spinnerEstados.setAdapter(adapter);



        final Activity curView = this;

        spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estado = spinnerEstados.getSelectedItem().toString();;

                ArrayList<String> cidades = getCidades("estados-cidades.json", estado);
                Spinner spinnerCidades=(Spinner) findViewById(R.id.spinnerCidade);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(curView, R.layout.spinner_layout, R.id.textView, cidades);
                spinnerCidades.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayList<String> cidades = getCidades("estados-cidades.json", "AC");
        Spinner spinnerCidades=(Spinner) findViewById(R.id.spinnerCidade);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, cidades);
        spinnerCidades.setAdapter(adapter2);

        ArrayList<String> bancos = getBandeiras("bandeiras.json");
        Spinner spinnerBancos=(Spinner) findViewById(R.id.spinnerBandeira);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textView, bancos);
        spinnerBancos.setAdapter(adapter3);
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
                nomeCidade = jsonCidades.getString(i);
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

    public void cancelar(View v){
        finish();
    }

    public void voltar(View v) {
        super.onBackPressed();
    }

    /*public void onClick(View v) {
        Button btn_proximo = (Button) findViewById(R.id.btn_proximo);
        Intent it = new Intent(this, CadastroVendedor.class);
        startActivity(it);
    }*/
}