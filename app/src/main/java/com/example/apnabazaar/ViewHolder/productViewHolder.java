package com.example.apnabazaar.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.itemClickListener;

public class productViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName,productDesc,productPrice;
    public ImageView imageView;
    public itemClickListener listener;

    public productViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imageView6);
        productName = (TextView) itemView.findViewById(R.id.textView9);
        productDesc = (TextView)itemView.findViewById(R.id.textView10);
        productPrice = (TextView) itemView.findViewById(R.id.textView11);
    }

    public void setItemClickListener(itemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        listener.OnClick(v,getAdapterPosition(),false);
    }
}
