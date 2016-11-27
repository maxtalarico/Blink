package com.example.marcel.blink_mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class VendedorHome extends Fragment implements OnClickListener{
    public static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_vendedor, container, false);

        Fragment historyFragment = new HistoryVendedor();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_history_vendedor, historyFragment).commit();


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
               fragment = new GerarVenda();
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
}