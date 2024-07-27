package com.app.vkcommerce.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vkcommerce.R;
import com.app.vkcommerce.adapter.ProductAdapter;
import com.app.vkcommerce.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rcProducts;
    ProductAdapter adapter = new ProductAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rcProducts = findViewById(R.id.rc_products);
        rcProducts.setAdapter(adapter);
        Button btnLogout = findViewById(R.id.buttonLogout);
        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        fetchProducts();
    }

    private void fetchProducts() {
        db.collection("products")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w("SRD", "Listen failed.", e);
                        return;
                    }

                    List<Product> products = new ArrayList<>();
                    if (value != null) {
                        products = value.toObjects(Product.class); // Serialization from Firestore to Our Class
                        adapter.setProducts(products);
                    }
                    Log.d("SRD", "Current cites in CA: " + products);
                });

    }
}