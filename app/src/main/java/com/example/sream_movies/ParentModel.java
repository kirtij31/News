package com.example.sream_movies;

import java.util.ArrayList;
import java.util.List;

public class ParentModel {

    String title;
    ArrayList<ChildModel> list;

    public ParentModel(String title, ArrayList<ChildModel> list) {
        this.title = title;
        this.list = list;
    }
}
