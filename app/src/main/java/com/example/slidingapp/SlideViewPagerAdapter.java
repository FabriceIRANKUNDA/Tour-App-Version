package com.example.slidingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewPagerAdapter extends PagerAdapter {
    Context ctx;

    public SlideViewPagerAdapter(Context myCtx) {
        this.ctx = myCtx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService((ctx.LAYOUT_INFLATER_SERVICE));
        View view = layoutInflater.inflate(R.layout.slide_screen, container, false);

        ImageView logo = view.findViewById(R.id.logo);
        ImageView ind1 = view.findViewById(R.id.ind1);
        ImageView ind2 = view.findViewById(R.id.ind2);
        ImageView ind3 = view.findViewById(R.id.ind3);


        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);

        ImageView next = view.findViewById(R.id.next);
        ImageView back = view.findViewById(R.id.back);
        Button btn = view.findViewById(R.id.btnGetStarted);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position-1);
            }
        });

        switch (position){
            case 0:
                logo.setImageResource(R.drawable.travel);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Booking place");
                desc.setText("Travel and save on the world’s best hotels and view nature with HazaTravel!");
                back.setVisibility(view.GONE);
                next.setVisibility(view.VISIBLE);
                break;
            case 1:
                logo.setImageResource(R.drawable.family);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Family life's matter");
                desc.setText("Spend fabulous time with your beloved ones!");
                back.setVisibility(view.VISIBLE);
                next.setVisibility(view.VISIBLE);
                break;
            case 2:
                logo.setImageResource(R.drawable.logo3);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);

                title.setText("Variety ways to pay");
                desc.setText("Let go cashless and fight COVID-19 together!!");
                back.setVisibility(view.VISIBLE);
                next.setVisibility(view.GONE);
                break;
        }


        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
