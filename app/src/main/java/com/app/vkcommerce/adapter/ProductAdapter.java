package com.app.vkcommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vkcommerce.R;
import com.app.vkcommerce.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface ProductAdapterListener {
        void onProductClick(Product product);
        void onAddClick(Product product, int position);
        void onRemoveClick(Product product, int position);
    }

    private ArrayList<Product> products;
    ProductAdapterListener productAdapterListener;

    public ProductAdapter(ArrayList<Product> products, ProductAdapterListener productAdapterListener) {
        this.products = products;
        this.productAdapterListener = productAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);

        return new ViewHolder(view, productAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.setValues(product);
        /*holder.textName.setText(product.getName());
        Glide.with(holder.imgProduct.getContext())
                .load(product.getImageUri())
                .centerCrop()
                .into(holder.imgProduct);
        holder.txtQty.setText(String.valueOf(product.getOrderQty()));*/
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textName;
        public final ImageView imgProduct;
        public final TextView txtQty;
        public ImageButton btnAdd;
        public ImageButton btnRemove;

        public ViewHolder(View view, ProductAdapterListener productAdapterListener) {
            super(view);
            textName = view.findViewById(R.id.name);
            imgProduct = view.findViewById(R.id.image);
            txtQty = view.findViewById(R.id.txtQty);
            btnAdd = view.findViewById(R.id.btnAdd);
            btnRemove = view.findViewById(R.id.btnRemove);
        }

        public void setValues(Product product) {
            textName.setText(product.getName());
            Glide.with(imgProduct.getContext())
                    .load(product.getImageUri())
                    .centerCrop()
                    .into(imgProduct);
            txtQty.setText(String.valueOf(product.getOrderQty()));
            btnAdd.setOnClickListener(v -> {
                if (productAdapterListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        productAdapterListener.onAddClick(products.get(position), position);
                    }
                }
            });
            btnRemove.setOnClickListener(v -> {
                if (productAdapterListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        productAdapterListener.onRemoveClick(products.get(position), position);
                    }
                }
            });
        }
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }
}

