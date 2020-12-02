package com.example.slidingapp;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class NotificationFragment extends Fragment {
    EditText tourName, tourPrice, tourDesc;
    Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        tourName = view.findViewById(R.id.tourName);
        tourPrice = view.findViewById(R.id.tPrice);
        tourDesc = view.findViewById(R.id.tourDesc);

        btnAdd = view.findViewById(R.id.addButton);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "{"+
                        "\"nam\"" + "\"" + tourName.getText().toString() + "\","+
                        "\"price\"" + "\"" + tourPrice.getText().toString() + "\","+
                        "\"description\"" + tourDesc.getText().toString() + "\"" +
                        "}";

                tourDesc.setText("");
                tourName.setText("");
                tourPrice.setText("");
                submitData(data);
            }
        });
        return view;
    }

    private void submitData(String data) {
        final String saveData = data;
        String url = "http://jsonplaceholder.typicode.com/posts";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest strRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(getContext(), obj.toString(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    return  saveData == null ? null : saveData.getBytes("utf-8");
                }catch (UnsupportedEncodingException uee){
                    return  null;
                }
            }
        };

        requestQueue.add(strRequest);

    }


}
