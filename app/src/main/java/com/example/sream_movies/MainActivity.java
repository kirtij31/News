package com.example.sream_movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MoreClickListener{


    String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=4c5644188b9042008babda3162b2aa6e";

    RecyclerView recyclerView;

    ParentAdapter parentAdapter ;

    ArrayList<ChildModel> childModels;
    ArrayList<ParentModel> parentModels = new ArrayList<>();

    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().show();

        recyclerView = findViewById(R.id.parent_rv);
        parentAdapter = new ParentAdapter(parentModels, this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parentAdapter);

        fetch();

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
                    name = source_jsonObject.getString("name");

                    if(!map.containsKey(name)){

                        childModels = new ArrayList<>();
                        childModels.add(itemModel);

                        ParentModel parentModel = new ParentModel(name,childModels);
                        parentModels.add(parentModel);
                        parentAdapter.notifyDataSetChanged();
                        map.put(name,childModels );
                    }else{
                        childModels = map.get(name);
                        assert childModels != null;
                        childModels.add(itemModel);
                        parentAdapter.notifyDataSetChanged();
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> Log.e("errors","response")){
            @Override
            public Map<String, String> getHeaders() {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0");
            return headers;
        }};

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onMoreClicked(ArrayList<ChildModel> childModelArrayList, String title) {
        Intent intent = new Intent(MainActivity.this,ParentFullScreen.class);
        intent.putExtra("list",childModelArrayList);
        intent.putExtra("title",title);
        startActivity(intent);
    }

}