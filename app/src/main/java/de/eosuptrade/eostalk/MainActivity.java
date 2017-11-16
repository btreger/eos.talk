package de.eosuptrade.eostalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String TAG = MainActivity.class.getSimpleName();

    ViewGroup gruppe1;
    View hello;
    EditText eingabe;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gruppe1 = (ViewGroup) findViewById(R.id.gruppe1);
        hello = findViewById(R.id.text_hello);
        hello.setOnClickListener(this);
        eingabe = (EditText) findViewById(R.id.text_eingabe);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick v=" + v.getId());
        if (v.getId() == R.id.button) {
            String name = eingabe.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.hello) + " " + name);
            builder.setIcon(android.R.drawable.ic_input_add);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        } else if (v.getId() == R.id.text_hello) {
            Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SubActivity.class);
            startActivity(intent);
        }
    }
}
