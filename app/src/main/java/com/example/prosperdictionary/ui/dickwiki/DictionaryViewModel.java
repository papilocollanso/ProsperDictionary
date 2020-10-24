package com.example.prosperdictionary.ui.dickwiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DictionaryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DictionaryViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}