package com.example.prosperdictionary;

import android.os.Parcel;
import android.os.Parcelable;

public class DictObjectModel implements Parcelable {
    String word;
    String defn;
    public DictObjectModel(){

    }


    public DictObjectModel(String word,String defn) {
        this.word = word;
        this.defn=defn;

    }

    protected DictObjectModel(Parcel in) {
        word = in.readString();
        defn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(defn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DictObjectModel> CREATOR = new Creator<DictObjectModel>() {
        @Override
        public DictObjectModel createFromParcel(Parcel in) {
            return new DictObjectModel(in);
        }

        @Override
        public DictObjectModel[] newArray(int size) {
            return new DictObjectModel[size];
        }
    };

    public String getWord(){
        return word;
    }
    public String getDefn(){
        return  defn;
    }

}
