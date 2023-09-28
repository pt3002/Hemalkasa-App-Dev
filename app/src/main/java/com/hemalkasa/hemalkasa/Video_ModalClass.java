package com.hemalkasa.hemalkasa;

import android.os.Parcel;
import android.os.Parcelable;

public class Video_ModalClass implements Parcelable {
    String path;
    String title;

    public Video_ModalClass(String path, String title) {
        this.path = path;
        this.title = title;
    }

    protected Video_ModalClass(Parcel in) {
        path = in.readString();
        title = in.readString();
    }

    public static final Creator<Video_ModalClass> CREATOR = new Creator<Video_ModalClass>() {
        @Override
        public Video_ModalClass createFromParcel(Parcel in) {
            return new Video_ModalClass(in);
        }

        @Override
        public Video_ModalClass[] newArray(int size) {
            return new Video_ModalClass[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(title);
    }
}
