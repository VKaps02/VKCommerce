package com.app.vkcommerce.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vkcommerce.R;
import com.app.vkcommerce.adapter.ProductAdapter;
import com.app.vkcommerce.model.CartItem;
import com.app.vkcommerce.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rcProducts;
    ProductAdapter adapter = new ProductAdapter(new ArrayList<>(), this);
    //List<Product> products = new ArrayList<>();
    Map<String, Product> products = new HashMap<>();

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

                    if (value != null) {
                        for (QueryDocumentSnapshot doc : value) {
                            Product product = doc.toObject(Product.class);
                            product.setDocId(doc.getId());
                            products.put(doc.getId(), product);
                        }
                        fetchUserCart();
                    }
                    Log.d("SRD", "Current cites in CA: " + products);
                });

    }

    private void fetchUserCart() {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId != null) {
            db.collection("users").document(userId)
                    .collection("userCart")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("SRD", document.getId() + " => " + document.getData());
                                    CartItem cartItem = document.toObject(CartItem.class);
                                    Product product = products.get(cartItem.getDocId());
                                    if (product != null) {
                                        if (cartItem.getOrderQty() > product.getStockQty()) {
                                            product.setOrderQty(product.getStockQty());
                                        } else {
                                            product.setOrderQty(cartItem.getOrderQty());
                                        }
                                        products.put(document.getId(), product);
                                    }
                                }
                                adapter.setProducts(new ArrayList<Product>(products.values()));
                                Log.d("SRD", "Product list : " + products);
                            } else {
                                Log.d("SRD", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductClick(Product product) {

    }

    @Override
    public void onAddClick(Product product, int position) {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId != null) {
            if (product.getStockQty() > product.getOrderQty()) {
                product.setOrderQty(product.getOrderQty() + 1);
                adapter.notifyItemChanged(position);
                CartItem cartItem = new CartItem(product.getId(), product.getDocId(), product.getOrderQty());

                db.collection("users").document(userId)
                        .collection("userCart").document(product.getDocId())
                        .set(cartItem)
                        .addOnSuccessListener(aVoid -> {
                        })
                        .addOnFailureListener(e -> {
                            Log.w("SRD", "Error writing document", e);
                            product.setOrderQty(product.getOrderQty() - 1);
                            adapter.notifyItemChanged(position);
                        });
            } else {
                Toast.makeText(this, "Only " + product.getStockQty() + " left", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemoveClick(Product product, int position) {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId != null) {
            if (product.getOrderQty() > 1) {
                product.setOrderQty(product.getOrderQty() - 1);
                adapter.notifyItemChanged(position);
                CartItem cartItem = new CartItem(product.getId(), product.getDocId(), product.getOrderQty());
                db.collection("users").document(userId)
                        .collection("userCart").document(product.getDocId())
                        .set(cartItem)
                        .addOnSuccessListener(aVoid -> {
                        })
                        .addOnFailureListener(e -> {
                            Log.w("SRD", "Error writing document", e);
                            product.setOrderQty(product.getOrderQty() + 1);
                            adapter.notifyItemChanged(position);
                        });
            } else if (product.getOrderQty() == 1) {
                product.setOrderQty(product.getOrderQty() - 1);
                adapter.notifyItemChanged(position);
                db.collection("users").document(userId)
                        .collection("userCart").document(product.getDocId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("SRD", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("SRD", "Error deleting document", e);
                                product.setOrderQty(product.getOrderQty() + 1);
                                adapter.notifyItemChanged(position);
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
        }
    }
}