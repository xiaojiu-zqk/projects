package com.minsu.models.api;


import com.minsu.models.beans.ChaptersBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface WanApi {

    @GET("wxarticle/chapters/json")
    Flowable<ChaptersBean> getChapters();


}
