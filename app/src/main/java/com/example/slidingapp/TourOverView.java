package com.example.slidingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TourOverView extends AppCompatActivity {
    EditText tourName, desc, price;
    Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tour);

        tourName = findViewById(R.id.tour);
        price = findViewById(R.id.editPrice);
        desc = findViewById(R.id.editDesc);

        Intent intent = getIntent();

        tourName.setText(intent.getStringExtra("name"));
        price.setText(intent.getStringExtra("price"));
        desc.setText(intent.getStringExtra("desc"));

        btnUpdate = findViewById(R.id.editButton);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Loading spinner = new Loading(TourOverView.this);
                submitData(tourName.getText().toString(), price.getText().toString(), desc.getText().toString(),spinner);

                desc.setText("");
                tourName.setText("");
                price.setText("");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public void submitData(final String name, final String price, final String description, final Loading spinner) {
        spinner.startLoadingDialog();
        String url = "https://wetouryou.herokuapp.com/api/v1/tours/" + getIntent().getStringExtra("id");
        JSONObject ob = new JSONObject();
        try{
            ob.put("name", name);
            ob.put("price", Integer.parseInt(price));
            ob.put("summary", description);
        }catch (Exception e){

        }


        RequestQueue requestQueue = Volley.newRequestQueue(TourOverView.this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PATCH, url, ob, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    spinner.dismissDialog();
                    Toast.makeText(TourOverView.this, "Tour updated successfully!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    spinner.dismissDialog();
                    Toast.makeText(TourOverView.this, "Error" + e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.dismissDialog();
                error.printStackTrace();
                Toast.makeText(TourOverView.this, "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjReq);
    };


}

