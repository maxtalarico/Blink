package com.example.marcel.blink_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marcel on 23/11/2016.
 */

public class HistoryVendedorListAdapter extends BaseAdapter implements ListAdapter {
    final static String BASE_URL = "http://blink-brunopansani-1.c9users.io/";

    private ArrayList<String> list = new ArrayList<String>();
    private HistoryVendedor context;

    public HistoryVendedorListAdapter(ArrayList<String> list, HistoryVendedor context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        long r = 0;
        return r;
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.history_vendedor_list_item, parent, false);
        }

        //Handle TextView and display string from your list
        TextView tvNomeBandeira = (TextView)view.findViewById(R.id.nomeEstabelecimentoData);
        tvNomeBandeira.setText(list.get(position).split(";")[1] + " - " + list.get(position).split(";")[2]);

        TextView tvNumeroCartao = (TextView)view.findViewById(R.id.valor);
        tvNumeroCartao.setText(list.get(position).split(";")[3]);

        return view;
    }
}
