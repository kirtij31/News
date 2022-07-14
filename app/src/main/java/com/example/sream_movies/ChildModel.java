package com.example.sream_movies;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildModel implements Parcelable {
    public ChildModel(String image_url) {
        this.image_url = image_url;
    }

    String image_url;

    protected ChildModel(Parcel in) {
        image_url = in.readString();
    }

    public static final Creator<ChildModel> CREATOR = new Creator<ChildModel>() {
        @Override
        public ChildModel createFromParcel(Parcel in) {
            return new ChildModel(in);
        }

        @Override
        public ChildModel[] newArray(int size) {
            return new ChildModel[size];
        }
    };

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image_url);
    }
}
