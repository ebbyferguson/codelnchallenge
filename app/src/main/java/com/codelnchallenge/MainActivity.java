package com.codelnchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OkHttpClient okHttpClient;
    private Request request;
    private static final String API_URL = "https://jsonplaceholder.typicode.com/photos";
    private static final String TAG = "MAIN ACTIVITY";
    Gson g = new Gson();
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);


        ListData[] myListData = new ListData[]{

        };

        getData();
    }

    public void getData(){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.Theme_AppCompat_DayNight);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                progressDialog.dismiss();
                // Observe reason of failure using
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    // Use response here
                    final String res = response.body().string();
                    Log.i(TAG, res);


                    JsonReader reader = new JsonReader(new StringReader(res));
                    reader.setLenient(true);

                    ListData[] resp = g.fromJson(reader, ListData[].class);

                    adapter = new MyAdapter(resp);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                }
                else{
                    // Observe error

                }
            }
        });
    }

}
