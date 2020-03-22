package com.example.doctalks2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context = context;
    }

    public int[] slide_images = {R.drawable.s1,R.drawable.s2,R.drawable.s3};
    //public String[] slide_headings = {"Reliable e-Prescription","Easy storage and management of prescriptions","Easy and Hassle free"};
    //public String[] slide_descriptions = {"DOC Talks eliminates handwriting errors and gives both physician and pharmacist access to a patient’s prescription history to reduce the chance of the wrong drug being dispensed"
            //,"Add your monthly expenses/spendings and keep a track of your expenditure","Save money for a better tomorrow"};



    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides,container,false);

        ImageView imageView = (ImageView)view.findViewById(R.id.iv);
        //TextView textView = (TextView)view.findViewById(R.id.tv1);
        //TextView textView1 = (TextView)view.findViewById(R.id.tv2);

        imageView.setImageResource(slide_images[position]);
        //textView.setText(slide_descriptions[position]);
        //textView1.setText(slide_headings[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
