package com.example.admin.codingtest5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.codingtest5.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MyItemListAdapter extends RecyclerView.Adapter<MyItemListAdapter.ViewHolder> {

    private static final String TAG = "MyItemListAdapter";
    Context context;
    List<Recipe> items = new ArrayList<>();

    public MyItemListAdapter(List<Recipe> itemList) {
        this.items = itemList;
    }

    @Override
    public MyItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.list_item, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(MyItemListAdapter.ViewHolder holder, final int position) {
        Recipe r = items.get( position );

        holder.r = r;

        Glide.with( context )
                .load( r.getImage() )
                .into( holder.image );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        Recipe r;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked");
                    Toast.makeText( context, "" + r.getLabel(), Toast.LENGTH_SHORT).show();
                    //Mac's way
//                    Intent intent = new Intent(context, ProductDetailsActivity.class);
//                    intent.putExtra("item", item);
//                    context.startActivity(intent);
                }
            });

            image = itemView.findViewById( R.id.ivImage );
        }
    }
}
