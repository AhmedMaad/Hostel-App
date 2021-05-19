package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdsActivity extends AppCompatActivity {

    private ArrayList<ProductModel> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_apartments);

        loadProducts();

    }

    private void loadProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection(Constants.PRODUCTS);
        collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < documentSnapshots.size(); ++i) {
                    ProductModel productModel = documentSnapshots.get(i).toObject(ProductModel.class);
                    if (productModel.getUserId().equals(Constants.USER_ID))
                        products.add(productModel);
                }
                showProducts();
            }
        });
    }

    private void showProducts() {

        ListView listView = findViewById(android.R.id.list);
        // For populating list data
        CustomListView customListView = new CustomListView(this, products);
        listView.setAdapter(customListView);

    }

}
