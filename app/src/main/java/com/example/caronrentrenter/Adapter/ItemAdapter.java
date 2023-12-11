package com.example.caronrentrenter.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Detail;
import com.example.caronrentrenter.Domain.ItemDomain;
import com.example.caronrentrenter.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<ItemDomain> item;
    DecimalFormat formatter;
    Context context;

    public ItemAdapter(ArrayList<ItemDomain> item) {
        this.item = item;
        formatter=new DecimalFormat("###,###,###,###.##");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder,parent,false);
        context=parent.getContext();
        return new ViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(item.get(position).getTitle());
        holder.address.setText(item.get(position).getAddress());
        //holder.price.setText(item.get(position).getPrice());
        holder.price.setText(formatter.format(item.get(position).getPrice()));

        int drawableResourceId=holder.itemView.getResources().getIdentifier(item.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Detail.class);
                intent.putExtra("object",item.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,address,price;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            address=itemView.findViewById(R.id.address);
            price=itemView.findViewById(R.id.price);

            pic=itemView.findViewById(R.id.pic);
        }
    }

}

