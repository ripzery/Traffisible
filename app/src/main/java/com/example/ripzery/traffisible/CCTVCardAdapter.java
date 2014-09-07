package com.example.ripzery.traffisible;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ripzery.traffisible.JSONObjectClass.CCTV;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.List;

/**
 * Created by visit on 9/7/14 AD.
 */
public class CCTVCardAdapter extends BaseAdapter implements UndoAdapter {
    private MyActivity myActivity;
    private LayoutInflater inflater;
    private List<CCTV> listCCTV;
    private ImageLoaderConfiguration config;

    public CCTVCardAdapter(MyActivity context, List<CCTV> objects) {
        this.listCCTV = objects;
        this.myActivity = context;
        config = new ImageLoaderConfiguration.Builder(context)
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
    }

    @Override
    public int getCount() {
        return listCCTV.size();
    }

    @Override
    public Object getItem(int position) {
        return listCCTV.get(position);
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
            rowView = inflater.inflate(R.layout.cctv_cardview, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvCCTVEngName = (TextView) rowView.findViewById(R.id.tvCCTVEngName);
            viewHolder.tvCCTVThaiName = (TextView) rowView.findViewById(R.id.tvCCTVThaiName);
            viewHolder.tvCCTVTime = (TextView) rowView.findViewById(R.id.tvCCTVTime);
            viewHolder.ivCCTV = (ImageView) rowView.findViewById(R.id.ivCCTV);

            rowView.setTag(viewHolder);
        }

        if (inflater == null) {
            inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.cctv_cardview, null);

        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        CCTV cctv = listCCTV.get(position);
        String s = cctv.getName();
//        Log.d("test", s);
        holder.tvCCTVEngName.setText(s);
        holder.tvCCTVEngName.setTypeface(tfBold);

        s = cctv.getName_th();
        holder.tvCCTVThaiName.setText(s);
        holder.tvCCTVThaiName.setTypeface(tfMedium);
        s = cctv.getLastupdate();
        holder.tvCCTVTime.setText(s);
        holder.tvCCTVTime.setTypeface(tfLight);


        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(listCCTV.get(position).getUrl(), holder.ivCCTV);

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
        TextView tvCCTVEngName;
        TextView tvCCTVThaiName;
        TextView tvCCTVTime;
        ImageView ivCCTV;
    }
}
