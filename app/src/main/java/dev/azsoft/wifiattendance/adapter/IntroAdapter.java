package dev.azsoft.wifiattendance.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.models.Intro;


public class IntroAdapter extends PagerAdapter {

    final List<Intro> introList;
    final Context context;

    public IntroAdapter(Context context, List<Intro> introList) {
        this.context = context;
        this.introList = introList;
    }

    @Override
    public int getCount() {
        return introList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_item, container, false);
        ImageView imgIntro = view.findViewById(R.id.img_intro);
        TextView tvHeadline = view.findViewById(R.id.tv_intro_headline);
        TextView tvSubtitle = view.findViewById(R.id.tv_intro_subtitle);
        Intro intro = introList.get(position);
        imgIntro.setImageResource(intro.getImageId());
        tvHeadline.setText(intro.getHeadline());
        tvSubtitle.setText(intro.getSubtitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);

    }
}
