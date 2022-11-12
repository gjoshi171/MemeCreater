package com.ag.memecreater.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ImgFlipApi {
    @GET("get_memes")
    fun fetchTemplates(): Call<ImgFlipGetMemesResponse>

    @POST("caption_image")
    @FormUrlEncoded
    fun captionImage(
        @Field("template_id") template_id: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("text0") caption1: String,
        @Field("text1") caption2: String
    ) : Call<ImgFlipCaptionImageResponse>
}