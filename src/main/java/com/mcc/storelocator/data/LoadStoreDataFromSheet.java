package com.mcc.storelocator.data;

import android.util.Log;

import com.mcc.storelocator.api.HttpParams;
import com.mcc.storelocator.api.RetrofitClient;
import com.mcc.storelocator.listener.RetrofitDataLoadListener;
import com.mcc.storelocator.model.Store;
import com.mcc.storelocator.model.StoreList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadStoreDataFromSheet {
    private RetrofitDataLoadListener mListener;
    //locad data from sheet
    public void loadAllStoreData() {
        final ArrayList<Store> allStorelList = new ArrayList<>();
        RetrofitClient.getClient().getStoreList(HttpParams.SHEET_ID, HttpParams.SHEET_NAME_STORE).enqueue(new Callback<StoreList>() {
            @Override
            public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                try{

                    allStorelList.addAll(response.body().getStore());
                    AppConstant.ALL_STORE_LIST.addAll(allStorelList);
                    mListener.finishLoadData(allStorelList, true);

                }
                catch (Exception e){
                    e.getMessage();
                }


            }

            @Override
            public void onFailure(Call<StoreList> call, Throwable t) {
                Log.d("DataTesing", "Second Req DetailsDataNotFound " + t.toString());
            }
        });

    }

    public void setClickListener(RetrofitDataLoadListener mListener) {
        this.mListener = mListener;
    }

}
