package com.example.swipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
//기존 Bundle객체에 데이터 담은다음 넘겨서 화면간 정보 전달 이게 불편함
//데이터 공유하는 공간 생성
public class PageViewModel extends ViewModel {

    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<Integer> mCount = new MutableLiveData<>();

    public void setName(String name) {
        mName.setValue(name);
    }
    public LiveData<String> getName(){return mName;}

    public void setCount(int cnt) {
        mCount.setValue(cnt);
    }
    public LiveData<Integer> getmCount(){return mCount;}



}
