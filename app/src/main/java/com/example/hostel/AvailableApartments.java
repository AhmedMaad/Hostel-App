package com.example.hostel;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class AvailableApartments extends ListActivity {

    private ListView listView;
    private String[] titles = {"India", "China", "Nepal", "Bhutan"};
    private String[] prices = {"Delhi", "Beijing", "Kathmandu", "Thimphu"};
    private Integer[] imageid = {
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_apartments);

        // Setting header
        SearchView searchView = new SearchView(this);

        ListView listView = findViewById(android.R.id.list);
        listView.addHeaderView(searchView);

        // For populating list data
        CustomListView customListView = new CustomListView(this, titles, prices, imageid);
        listView.setAdapter(customListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(AvailableApartments.this, ApartmentDetails.class);
                startActivity(intent);
            }
        });
    }
}
