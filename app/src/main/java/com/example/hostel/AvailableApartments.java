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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AvailableApartments extends ListActivity {

    /*private String[] titles = {"India", "China", "Nepal", "Bhutan"};
    private String[] prices = {"Delhi", "Beijing", "Kathmandu", "Thimphu"};
    private Integer[] imageid = {
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home};*/

    private ArrayList<ProductModel> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_apartments);

        String productType = getIntent().getStringExtra(Constants.PRODUCT);
        loadProducts(productType);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(AvailableApartments.this, ApartmentDetails.class);
                startActivity(intent);
            }
        });

    }

}
