package com.example.ripzery.traffisible;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by visit on 9/1/14 AD.
 */
public class CardAdapter extends BaseAdapter {
    private MyActivity myActivity;
    private LayoutInflater inflater;
    private List<News> listNews;

    public CardAdapter(MyActivity context, List<News> objects) {
        this.listNews = objects;
        this.myActivity = context;
    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_layout, null);

        }

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvDes = (TextView) view.findViewById(R.id.tvDes);
        TextView tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
        TextView tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
        TextView tvFrom = (TextView) view.findViewById(R.id.tvFrom);
        TextView tvTo = (TextView) view.findViewById(R.id.tvTo);


        Typeface tfBold = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tfMedium = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface tfLight = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-Light.ttf");
        Typeface tfLightItalic = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-LightItalic.ttf");

        tvTitle.setTypeface(tfBold);
        tvDes.setTypeface(tfMedium);
        tvStartTime.setTypeface(tfLightItalic);
        tvEndTime.setTypeface(tfLightItalic);
        tvFrom.setTypeface(tfLight);
        tvTo.setTypeface(tfLight);


        News news = listNews.get(position);

        tvTitle.setText(news.getTitle());
        tvDes.setText(news.getDescription());
        tvStartTime.setText(news.getStarttime());
        tvEndTime.setText(news.getEndtime());

        return view;
    }
}
