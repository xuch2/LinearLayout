package com.example.recyclercash;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter
        extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> items = new ArrayList<>();
    OnProductItemClickListener listener;

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(
                R.layout.product_item,
                parent,
                false);
        return ViewHolder(itemView, this);
    }

    static  class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3, tv4;
        imageView imgView;

        public ViewHolder(View itemView,
                          final OnProductItemClickListener listener) {
            super(itemView);

            tv1 = (TextView)itemView.findViewByld(R.id.textView1);
            tv2 = (TextView)itemView.findViewByld(R.id.textView2);
            tv3 = (TextView)itemView.findViewByld(R.id.textView3);
            tv4 = (TextView)itemView.findViewByld(R.id.textView4);

            imgView = (imageView)itemView.findViewByld(R.id.textView1);

            itemView.setOnClickListener(new View.onClickListener())

        }

    }
            )
}
