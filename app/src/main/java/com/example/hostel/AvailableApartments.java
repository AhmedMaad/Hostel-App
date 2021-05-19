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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AvailableApartments extends AppCompatActivity {

    private ArrayList<ProductModel> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_apartments);

        String productType = getIntent().getStringExtra(Constants.PRODUCT);
        loadProducts(productType);

        setTitle("Available " + productType + 's');

    }

    private void loadProducts(String productType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection(Constants.PRODUCTS);
        collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < documentSnapshots.size(); ++i) {
                    ProductModel productModel = documentSnapshots.get(i).toObject(ProductModel.class);
                    if (productModel.getProductType().equals(productType))
                        products.add(productModel);
                }
                showProducts();
            }
        });
    }

    private void showProducts() {

        // Setting header
        SearchView searchView = new SearchView(this);

        ListView listView = findViewById(android.R.id.list);
        listView.addHeaderView(searchView);

        // For populating list data
        CustomListView customListView = new CustomListView(this, products);
        listView.setAdapter(customListView);

        //We are creating "position - 1" because the search view index is 0
        //as it associated with the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(AvailableApartments.this, ApartmentDetails.class);
                intent.putExtra(Constants.ADDRESS, products.get(position - 1).getAddress());
                intent.putExtra(Constants.PRICE, products.get(position - 1).getPrice());
                intent.putExtra(Constants.SPECIFICATIONS, products.get(position - 1).getSpecifications());
                intent.putExtra(Constants.NAME, products.get(position - 1).getName());
                intent.putExtra(Constants.PHONE_NUMBER, products.get(position - 1).getPhone());
                startActivity(intent);
            }
        });

    }

}
