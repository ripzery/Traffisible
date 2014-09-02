package com.example.ripzery.traffisible;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.IconTextView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.List;

/**
 * Created by visit on 9/1/14 AD.
 */
public class CardAdapter extends BaseAdapter implements UndoAdapter {
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

        View rowView = view;
        Typeface tfBold = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tfMedium = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface tfLight = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-Light.ttf");
        Typeface tfLightItalic = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Roboto-LightItalic.ttf");
        if (rowView == null) {
            LayoutInflater inflater = myActivity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
            viewHolder.icon = (IconTextView) rowView.findViewById(R.id.iconify);
            viewHolder.tvDes = (TextView) rowView.findViewById(R.id.tvDes);
            viewHolder.tvStartTime = (TextView) rowView.findViewById(R.id.tvTime);

            rowView.setTag(viewHolder);
        }

        if (inflater == null) {
            inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_layout, null);

        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        News news = listNews.get(position);
        String s = news.getTitle();
        Log.d("test", s);
        holder.tvTitle.setText(s);
        holder.tvTitle.setTypeface(tfBold);

        if (s.equals("อุบัติเหตุ")) {
            holder.icon.setText("{fa-exclamation-circle}");
        } else if (s.equals("การก่อสร้าง")) {
            holder.icon.setText("{fa-gavel}");
        } else if (s.equals("จราจรติดขัด") || s.equals("จราจรชะลอตัว")) {
            holder.icon.setText("{fa-car}");
        }
        s = news.getDescription();
        holder.tvDes.setText(s.replace("\n", "").replace(" ", ""));
        holder.tvDes.setTypeface(tfMedium);
        s = news.getStarttime();
        holder.tvStartTime.setText(s);
        holder.tvDes.setTypeface(tfLight);

        return rowView;
    }

    @NonNull
    @Override
    public View getUndoView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        View undoLayout;
        undoLayout = inflater.inflate(R.layout.undo_view, null);
        return undoLayout;
    }

    @NonNull
    @Override
    public View getUndoClickView(@NonNull View view) {

        return view;
    }

    public static class ViewHolder {
        IconTextView icon;
        TextView tvTitle;
        TextView tvDes;
        TextView tvStartTime;
    }
}
