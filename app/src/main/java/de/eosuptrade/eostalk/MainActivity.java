package de.eosuptrade.eostalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    final static String TAG = MainActivity.class.getSimpleName();

    ViewGroup gruppe1;
    View hello;
    EditText eingabe;
    Button button;
    ListView liste;
    List<Haltestelle> haltestellenListe = new ArrayList<>();
    private HaltestellenAdapter adapter;

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
        liste = (ListView) findViewById(R.id.liste);
        adapter = new HaltestellenAdapter(this, haltestellenListe);
        liste.setAdapter(adapter);
        liste.setOnItemClickListener(this);
        TextView test = new TextView(this);
        test.setText("test");
        gruppe1.addView(test);
    }


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick v=" + v.getId());
        if (v.getId() == R.id.button) {
            String name = eingabe.getText().toString();
            try {
                name = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "https://shop-qs.hvv.de/mobile.php/location/autocomplete/fahrinfo?format=json&q=" + name;
            Log.v(TAG,"url=" + url);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "response=" + response);
                    Gson gson = new Gson();
                    List<Haltestelle> list = gson.fromJson(response, new TypeToken<List<Haltestelle>>(){}.getType());
                    haltestellenListe.clear();
                    haltestellenListe.addAll(list);
                    adapter.notifyDataSetChanged();
                    for (Haltestelle halt: list) {
                        Log.v(TAG, "halt=" + halt);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "error=" + error.getMessage());
                    haltestellenListe.clear();
                    adapter.notifyDataSetChanged();
                }
            });
            queue.add(request);
        } else if (v.getId() == R.id.text_hello) {
            Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SubActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Haltestelle hst = (Haltestelle) adapter.getItem(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Haltestelle");
        builder.setMessage(hst.getName() + ", " + hst.getRegion());
        builder.create().show();
    }
}
