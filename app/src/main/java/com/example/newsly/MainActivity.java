package com.example.newsly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoreClickListener{


    String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=4c5644188b9042008babda3162b2aa6e";

    RecyclerView recyclerView;

    ParentAdapter parentAdapter ;

    ArrayList<ChildModel> childModels;
    ArrayList<ParentModel> parentModels = new ArrayList<>();


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

        Call<NewsList> newsCall = NewsService.getInstance().myMemeInterface().getNews("in");
        newsCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                NewsList newsList = response.body();
                System.out.print(newsList);
                List<Article> articlesArrayList = newsList.getArticles();


               for(int i=0;i<articlesArrayList.size();i++){
                 Article articles = articlesArrayList.get(i);
                 String string = articles.getUrlToImage();
                 ChildModel itemModel = new ChildModel(string);

                 Source source = articles.getSource();
                 String name = source.getName();


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

            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {

            }
        });


    }

    @Override
    public void onMoreClicked(ArrayList<ChildModel> childModelArrayList, String title) {
        Intent intent = new Intent(MainActivity.this,ParentFullScreen.class);
        intent.putExtra("list",childModelArrayList);
        intent.putExtra("title",title);
        startActivity(intent);
    }

}