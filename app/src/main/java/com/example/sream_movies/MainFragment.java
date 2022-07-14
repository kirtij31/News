package com.example.sream_movies;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainFragment extends Fragment {


    String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=4c5644188b9042008babda3162b2aa6e";


    RecyclerView recyclerView;
    ArrayList<ParentModel> parentModels;
    ParentAdapter parentAdapter;


    ArrayList<ChildModel> childModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.parent_rv);


        parentModels = new ArrayList<>();
       parentAdapter = new ParentAdapter(parentModels,getContext());
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       recyclerView.setAdapter(parentAdapter);

       fetch();

        return view;
    }


    public void fetch(){

        HashMap<String,ArrayList<ChildModel>> map = new HashMap<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("articles");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);



                    String string = jsonObject.getString("urlToImage");
                    ChildModel itemModel = new ChildModel(string);

                    JSONObject source_jsonObject = jsonObject.getJSONObject("source");
                    String name = source_jsonObject.getString("name");

                    if(!map.containsKey(name)){
                        childModels = new ArrayList<>();
                        childModels.add(itemModel);
                        ParentModel parentModel = new ParentModel(name,childModels);
                    parentModels.add(parentModel);
                        parentAdapter.notifyDataSetChanged();
                        map.put(name,childModels );
                    }else{
                        childModels = map.get(name);
                        childModels.add(itemModel);
                        parentAdapter.notifyDataSetChanged();
                    }



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }, error -> Log.e("errors","response")){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0");
            return headers;
        }};

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

}