package com.example.newsly;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {

    ArrayList<ParentModel> parentModelList;
    Context context;
    MoreClickListener moreClickListener;

    public ParentAdapter(ArrayList<ParentModel> parentModelList, Context context, MoreClickListener moreClickListener) {
        this.parentModelList = parentModelList;
        this.context = context;
        this.moreClickListener = moreClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent,parent,false);
        ViewHolder viewHolder =  new ViewHolder(view);
        viewHolder.more_text.setOnClickListener(view1 -> moreClickListener.onMoreClicked(parentModelList.get(viewHolder.getAdapterPosition()).getList()
                                        ,viewHolder.title_text.getText().toString()    ));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title_text.setText(parentModelList.get(position).title);

        ChildAdapter childAdapter;
        childAdapter = new ChildAdapter(parentModelList.get(position).getList(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.child_rv.setLayoutManager(linearLayoutManager);
        holder.child_rv.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();

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

interface MoreClickListener{
    void onMoreClicked(ArrayList<ChildModel> childModels, String title);
}

