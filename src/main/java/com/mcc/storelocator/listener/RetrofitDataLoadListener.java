package com.mcc.storelocator.listener;

import com.mcc.storelocator.model.Store;

import java.util.ArrayList;

public interface RetrofitDataLoadListener {
    void finishLoadData(ArrayList<Store> dataList, boolean isSuccessful);

}
