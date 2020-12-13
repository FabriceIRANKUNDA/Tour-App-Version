package com.example.slidingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.Adapter;
import Modal.Tour;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Tour> tours;
    Context cxt;
    View view;
    private static String JSON_UR = "https://wetouryou.herokuapp.com/api/v1/tours?fields=name,difficulty,price,summary,imageCover";

    public HomeFragment(Context cxt){
        this.cxt = cxt;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.toursContainer);
        final Loading spinner = new Loading(getActivity());
        spinner.startLoadingDialog();
        tours = new ArrayList<>();

        ConnectivityManager check = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = check.getAllNetworkInfo();
        boolean connected = false;
        for(int index = 0; index < info.length; index++){
            if(info[index].getState() == NetworkInfo.State.CONNECTED){
                connected = true;
            }
        }
        if(connected){
            loadTours(spinner);
        }else{
            Toast.makeText(getContext(), "Connect to the internet", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void loadTours(final Loading spinner) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_UR, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObj = response.getJSONObject("data");
                    JSONArray resArr = resObj.getJSONArray("data");

                    for (int index = 0; index < resArr.length(); index++) {
                            JSONObject tourObject = resArr.getJSONObject(index);
                            Tour tour = new Tour();
                            tour.setName(tourObject.getString("name").toString());
                            tour.setDesc(tourObject.getString("summary").toString());
                            tour.setPrice("Price: " + tourObject.getString("price").toString() + "$");
                            tour.setImage(tourObject.getString("imageCover").toString());
                            tour.setTourId(tourObject.getString("_id").toString());
                            tours.add(tour);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                final Adapter recyclerViewAdapter = new Adapter( getActivity(), cxt, tours);
                recyclerView.setAdapter(recyclerViewAdapter);

                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        recyclerViewAdapter.deleteItem(viewHolder.getAdapterPosition(), tours.get(viewHolder.getAdapterPosition()).getTourId());
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }).attachToRecyclerView(recyclerView);

                spinner.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Connect to the internet, If you're connect try again later!.", Toast.LENGTH_LONG).show();

                spinner.dismissDialog();
            }
        });

        queue.add(jsonObjectRequest);
}
}
