package com.example.sream_movies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FullScreenFragment extends Fragment {


//    String url="https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/4THCYSSRWUI6XIPV7WXSRT6KSA.jpg&w=1440";
    String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=4c5644188b9042008babda3162b2aa6e";
    Toolbar toolbar;
    ArrayList<ChildModel> list;
    RecyclerView recyclerView;
    ChildAdapter childAdapter;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View view= inflater.inflate(R.layout.fragment_fullscreen, container, false);

        recyclerView =view.findViewById(R.id.full_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        Bundle bundle = this.getArguments();
        list=bundle.getParcelableArrayList("key");
        String string = bundle.getString("title");
        childAdapter=new ChildAdapter(list,getContext());
        recyclerView.setAdapter(childAdapter);


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(string);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                getActivity().onBackPressed();

            }
        });
        return view;


    }




}