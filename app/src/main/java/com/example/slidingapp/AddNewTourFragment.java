package com.example.slidingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class AddNewTourFragment extends Fragment {
    EditText tourName, tourPrice, tourDesc;
    Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_add_new_tour, container, false);
        tourName = view.findViewById(R.id.tourName);
        tourPrice = view.findViewById(R.id.tPrice);
        tourDesc = view.findViewById(R.id.tourDesc);

        btnAdd = view.findViewById(R.id.addButton);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Loading spinner = new Loading(getActivity());
                submitData(tourName.getText().toString(),tourPrice.getText().toString(),tourDesc.getText().toString(), spinner);
                tourDesc.setText("");
                tourName.setText("");
                tourPrice.setText("");



            }
        });
        return view;
    }

    public void submitData(final String name, final String price, final String description, final Loading spinner) {
        spinner.startLoadingDialog();
        String url = "https://wetouryou.herokuapp.com/api/v1/tours";

        JSONObject ob = new JSONObject();
        try{
            ob.put("name", name);
            ob.put("price", Integer.parseInt(price));
            ob.put("summary", description);
            ob.put("imageCover", "avatar");
        }catch (Exception e){

        }

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, ob, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    spinner.dismissDialog();
                    Toast.makeText(getContext(), response.getString("status"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    spinner.dismissDialog();
                    Toast.makeText(getContext(), "Big Error" + e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.dismissDialog();
                error.printStackTrace();
                Toast.makeText(getContext(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjReq);
        };

    }


