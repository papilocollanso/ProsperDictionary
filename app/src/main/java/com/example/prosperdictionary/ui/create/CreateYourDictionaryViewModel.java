package com.example.prosperdictionary.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateYourDictionaryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateYourDictionaryViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}