package com.example.marcel.blink_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import static android.app.Activity.RESULT_OK;

public class ClienteHome extends Fragment implements OnClickListener{
    public static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Fragment historyFragment = new History();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_history, historyFragment).commit();


        Button btnPagamento = (Button)rootView.findViewById(R.id.btn_home_pagamento);
        btnPagamento.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //do what you want to do when button is clicked
       switch (v.getId()) {
           case R.id.btn_home_pagamento:
               callZXing(v);
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

    public void callZXing(View view){
        Intent it = new Intent(getActivity(), com.google.zxing.client.android.CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            Log.d("QR Code", "RESULTADO: "+data.getStringExtra("SCAN_RESULT")+" ("+data.getStringExtra("SCAN_FORMAT")+")");

            android.app.FragmentManager fm = getActivity().getFragmentManager();
            ConfirmacaoCompra confirmacaoCompra = new ConfirmacaoCompra ();

            Bundle b = new Bundle();
            b.putString("idCompra", data.getStringExtra("SCAN_FORMAT"));

            confirmacaoCompra.setArguments(b);

            confirmacaoCompra.show(fm, "Sample Fragment");
        }
    }
}