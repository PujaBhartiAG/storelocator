package com.mcc.storelocator.api;

import com.mcc.storelocator.model.News;
import com.mcc.storelocator.model.NewsList;
import com.mcc.storelocator.model.StoreList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(HttpParams.SHEET_API_END_POINT)
    Call<StoreList> getStoreList(@Query("id") String sheetId, @Query("sheet") String sheetName);

    @GET(HttpParams.SHEET_API_END_POINT)
    Call<NewsList> getNewsList(@Query("id") String sheetId, @Query("sheet") String sheetName);
}
