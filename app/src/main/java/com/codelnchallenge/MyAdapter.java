package com.codelnchallenge;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ListData[] listData;

    public MyAdapter(ListData[] listData){
        this.listData = listData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.music_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ListData myListData  = listData[position];
        holder.txtTitle.setText(listData[position].getTitle());
        Picasso.get().load(listData[position].getThumbnailUrl()).into(holder.imgThumbnail);
        holder.lytBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"item with ID "+myListData.getId()+ "was clicked", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView txtTitle;
        public RelativeLayout lytBody;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgThumbnail = itemView.findViewById(R.id.thumbnail);
            this.txtTitle = itemView.findViewById(R.id.title);
            this.lytBody = itemView.findViewById(R.id.body);
        }
    }
}
