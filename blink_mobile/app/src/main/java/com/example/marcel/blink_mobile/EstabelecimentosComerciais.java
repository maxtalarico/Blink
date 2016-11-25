package com.example.marcel.blink_mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Marcel on 27/09/2016.
 */

public class EstabelecimentosComerciais extends Fragment /*implements View.OnClickListener*/ {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_estabelecimentos_comerciais, container, false);

        Fragment estabelecimentosComerciaisList = new EstabelecimentosComerciaisList();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_estabelecimentos_comerciais_list, estabelecimentosComerciaisList).commit();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (v.getId()) {
                    case R.id.btn_cadastrar_estabelecimento:
                        fragment = new CadastroEstabelecimentoComercial();

                    default:
                        break;
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        };

        Button button = (Button) view.findViewById(R.id.btn_cadastrar_estabelecimento);
        button.setOnClickListener(listener);
        /*button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });*/


        return view;
    }

    /*public void onClick(View v) {

        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = new CadastroCartao();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }*/
}
