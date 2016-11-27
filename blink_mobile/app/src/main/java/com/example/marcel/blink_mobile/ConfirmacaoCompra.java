package com.example.marcel.blink_mobile;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Marcel on 26/11/2016.
 */

public class ConfirmacaoCompra extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirmacao_compra, container, false);
        getDialog().setTitle("Simple Dialog");

        Bundle b = getArguments();
        String idCompraString = b.getString("idCompra");
        Integer idCompra = Integer.parseInt(idCompraString);
        

        TextView tvQRCode = (TextView) rootView.findViewById(R.id.compra);
        tvQRCode.setText(idCompraString);

        Button confirm = (Button) rootView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }
}
