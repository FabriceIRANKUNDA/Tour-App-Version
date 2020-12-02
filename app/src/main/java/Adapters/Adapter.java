
package Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.slidingapp.Loading;
import com.example.slidingapp.MainActivity;
import com.example.slidingapp.R;
import com.example.slidingapp.TourOverView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modal.Tour;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    LayoutInflater inflater;
    Context cxt;
    List<Tour> tours;
    RequestOptions option;
    FragmentActivity act;


    public Adapter(FragmentActivity act, Context ctx, List<Tour> tour){
        this.inflater = LayoutInflater.from(ctx);
        this.cxt = ctx;
        this.tours = tour;
        this.act = act;
        option = new RequestOptions().override(470, 360).optionalCenterCrop().placeholder(R.drawable.family).error(R.drawable.family);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_tour, parent, false);
        return new ViewHolder(view);
    }

    public void deleteItem(int position, String ID){
        tours.remove(position);
        submitData(ID, new Loading(act));
    }

    public void submitData(final String id, final Loading spinner) {
        spinner.startLoadingDialog();
        String url = "https://wetouryou.herokuapp.com/api/v1/tours/" + id;

        RequestQueue requestQueue = Volley.newRequestQueue(cxt);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    spinner.dismissDialog();
                    Toast.makeText(cxt, "Tour deleted successfully", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    spinner.dismissDialog();
                    Toast.makeText(cxt, "Deleted successfully" , Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.dismissDialog();
                error.printStackTrace();
                Toast.makeText(cxt, "Deleted successfully", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjReq);
    };

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // Bind data to our custom layout

        holder.tourName.setText(tours.get(position).getName());
        holder.desc.setText(tours.get(position).getDesc());
        holder.price.setText(tours.get(position).getPrice());
        Glide.with(cxt).load(tours.get(position).getImage()).apply(option).into(holder.tourImageCover);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = holder.price.getText().toString().substring(7, holder.price.getText().toString().length() - 1);
                Intent intent = new Intent(cxt, TourOverView.class);
                intent.putExtra("name", holder.tourName.getText());
                intent.putExtra("desc", holder.desc.getText());
                intent.putExtra("price", p);
                intent.putExtra("id", tours.get(position).getTourId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                cxt.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tourName, price, desc;
        ImageView tourImageCover;
        Button btnDetail;
        CardView tour;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tourName = itemView.findViewById(R.id.tourTitle);
            price = itemView.findViewById(R.id.price);
            desc = itemView.findViewById(R.id.description);
            tourImageCover = itemView.findViewById(R.id.coverImage);
            btnDetail = itemView.findViewById(R.id.btnDetails);
            tour = itemView.findViewById(R.id.tourCard);
        }
    }
}
