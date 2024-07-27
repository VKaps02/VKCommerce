package com.app.vkcommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private ArrayList<Product> products;

    public ProductAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.getNameTextView().setText(product.getName());
        Glide.with(holder.getImgProduct().getContext())
                .load(product.getImageUri())
                .centerCrop()
                .into(holder.getImgProduct());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final ImageView imgProduct;

        public ViewHolder(View view) {
            super(view);

            textName = view.findViewById(R.id.name);
            imgProduct = view.findViewById(R.id.image);
        }

        public TextView getNameTextView() {
            return textName;
        }

        public ImageView getImgProduct() {
            return imgProduct;
        }
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }
}
