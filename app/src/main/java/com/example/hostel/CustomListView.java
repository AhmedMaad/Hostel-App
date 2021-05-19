package com.example.hostel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter {

    private ArrayList<ProductModel> products;
    private Activity context;

    public CustomListView(Activity context, ArrayList<ProductModel> products) {
        super(context, R.layout.row_item, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.row_item, parent, false);
        TextView titleTextView = row.findViewById(R.id.titleTextView);
        TextView priceTextView = row.findViewById(R.id.priceTextView);
        ImageView apartImageView = row.findViewById(R.id.apartImageView);
        titleTextView.setText(products.get(position).getAddress());
        priceTextView.setText(String.valueOf(products.get(position).getPrice()));
        Picasso
                .with(context)
                .load(products.get(position).getPicture())
                .into(apartImageView);
        return row;
    }
}
