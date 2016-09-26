package com.example.marcel.blink_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login()) {
                    Intent i = new Intent(Main.this, Drawer.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Login e/ou senha incorreto(s)",Toast.LENGTH_SHORT).show();
                }
            }
        };

        Button btn = (Button) findViewById(R.id.btnContinuar);
        btn.setOnClickListener(listener);
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

    public boolean login() {
        EditText user;
        EditText pass;

        user = (EditText)findViewById(R.id.txt_main_login);
        pass = (EditText)findViewById(R.id.txt_main_senha);

        if(user.getText().toString().equals( "123") && pass.getText().toString().equals( "123"))
            return true;
        else
            return false;
    }
}

