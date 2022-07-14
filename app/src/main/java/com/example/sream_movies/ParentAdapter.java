package com.example.sream_movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {

    ArrayList<ParentModel> parentModelList;
    Context context;
    ChildAdapter childAdapter;

    public ParentAdapter(ArrayList<ParentModel> parentModelList, Context context) {
        this.parentModelList = parentModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.ViewHolder holder, int position) {
        holder.title_text.setText(parentModelList.get(position).title);


        childAdapter = new ChildAdapter(parentModelList.get(position).list,context);
        holder.child_rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.child_rv.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();


        holder.more_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("key",parentModelList.get(position).list);
                bundle.putString("title",parentModelList.get(position).title);

                FullScreenFragment fullScreenFragment = new FullScreenFragment();
                fullScreenFragment.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,fullScreenFragment).addToBackStack(null).commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return parentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_text;
        TextView more_text;
        RecyclerView child_rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            more_text =itemView.findViewById(R.id.more_btn);
            title_text = itemView.findViewById(R.id.title_parent);
            child_rv=itemView.findViewById(R.id.child_rv);
        }
    }
}
