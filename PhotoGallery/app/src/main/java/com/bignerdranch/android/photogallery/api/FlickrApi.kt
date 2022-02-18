package com.bignerdranch.android.photogallery.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=5557bc261eefd85899b6aa58d5ebd270" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    fun fetchPhotos(): retrofit2.Call<FlickrResponse>

    @GET
    fun fetchUrlBytes(@Url url: String): retrofit2.Call<ResponseBody>
}