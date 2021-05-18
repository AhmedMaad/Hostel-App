package com.example.hostel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListView extends ArrayAdapter {
    private String[] titles;
    private String[] prices;
    private Integer[] imageid;
    private Activity context;

    public CustomListView(Activity context, String[] titles, String[] prices, Integer[] imageid) {
        super(context, R.layout.row_item, titles);
        this.context = context;
        this.titles = titles;
        this.prices = prices;
        this.imageid = imageid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView titleTextView = (TextView) row.findViewById(R.id.titleTextView);
        TextView priceTextView = (TextView) row.findViewById(R.id.priceTextView);
        ImageView apartImageView = (ImageView) row.findViewById(R.id.apartImageView);
        titleTextView.setText(titles[position]);
        priceTextView.setText(prices[position]);
        apartImageView.setImageResource(imageid[position]);
        return row;
    }
}
