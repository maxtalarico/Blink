package com.example.marcel.blink_mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment implements OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Get the reference of movies
        ListView historyList= (ListView) view.findViewById(R.id.history_home_listview);

        String[] history = {"History Test 1",
                            "History Test 2",
                            "History Test 3",
                            "History Test 4",
                            "History Test 5",
                            "History Test 6",
                            "History Test 7",
                            "History Test 8",
                            "History Test 9",
                            "History Test 10",
                            "History Test 11",
                            "History Test 12",
                            "History Test 13",
                            "History Test 14",
                            "History Test 15",
                            "History Test 16",
                            "History Test 17",
                            "History Test 18",
                            "History Test 19",
                            "History Test 20"};

        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_list_item_1, history);
        // Set The Adapter
        historyList.setAdapter(arrayAdapter);

        return view;
    }

    public void onClick(View v) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //do what you want to do when button is clicked
       switch (v.getId()) {
           case R.id.btnContinuar:
               break;

           default:
               break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}