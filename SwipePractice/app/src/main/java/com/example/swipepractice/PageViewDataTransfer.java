package com.example.swipepractice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewDataTransfer extends ViewModel {
    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<Integer> mCount = new MutableLiveData<>();

    public void setmName(String name){mName.setValue(name);}
    public LiveData<String> getmName(){ return mName;}
    public void setmCount(int cnt) {
        mCount.setValue(cnt);
    }
    public LiveData<Integer> getmCount(){return mCount;}
}
