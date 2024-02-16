package com.hemalkasa.hemalkasa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int images[] = {

            R.drawable.first_trimester,
            R.drawable.second_trimester,
            R.drawable.third_trimester

    };

    int headings[] = {

            R.string.first_trimester,
            R.string.second_trimester,
            R.string.third_trimester

    };

    int description[] = {

            R.string.desc_one,
            R.string.desc_two,
            R.string.desc_three,
    };

    public ViewPagerAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return  headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.titleImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.textTitle);
        TextView slideDesciption = (TextView) view.findViewById(R.id.textDescription);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDesciption.setText(description[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }
}
