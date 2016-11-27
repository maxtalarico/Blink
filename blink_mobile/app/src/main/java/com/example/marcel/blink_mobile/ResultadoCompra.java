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

public class ResultadoCompra extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_resultado_compra, container, false);
        getDialog().setTitle("Compra");

        Bundle b = getArguments();
        String resultado = b.getString("resultado");

        if(resultado.equals("Autorizada")) {
            TextView tvQRCode = (TextView) rootView.findViewById(R.id.resultado);
            tvQRCode.setText("Pagamento Autorizado!");
        } else {
            TextView tvQRCode = (TextView) rootView.findViewById(R.id.resultado);
            tvQRCode.setText("Pagamento Não Autorizado!");
        }

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
