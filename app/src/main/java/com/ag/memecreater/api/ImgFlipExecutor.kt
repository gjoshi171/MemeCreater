package com.ag.memecreater.api

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImgFlipExecutor {
    private  val api: ImgFlipApi


    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgflip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.api = retrofit.create(ImgFlipApi::class.java)
    }

    fun fetchTemplates(): LiveData<List<MemeTemplateItem>>{
        val responseLiveData: MutableLiveData<List<MemeTemplateItem>> = MutableLiveData()
        val imgFlipRequests : Call<ImgFlipGetMemesResponse> = this.api.fetchTemplates()

        imgFlipRequests.enqueue(object: Callback<ImgFlipGetMemesResponse>{
            override fun onResponse(
                call: Call<ImgFlipGetMemesResponse>,
                response: Response<ImgFlipGetMemesResponse>
            ) {
                Log.d(TAG,"Response received from ImgFlip templates endpoint")

                val imgFlipGetMemesResponse: ImgFlipGetMemesResponse? = response.body()
                val imgFlipGetMemesResponseData: ImgFlipGetMemesResponseData? = imgFlipGetMemesResponse?.data

                var memeTemplates: List<MemeTemplateItem> = imgFlipGetMemesResponseData?.templateItem ?: mutableListOf()
                memeTemplates= memeTemplates.filterNot { it.url.isBlank() }

               // Log.d(TAG,"ImgFlip templates: $memeTemplates")
                responseLiveData.value= memeTemplates
            }


            override fun onFailure(call: Call<ImgFlipGetMemesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return responseLiveData
    }
    fun captionImage(template_id: String, caption: String) : LiveData<ImgFlipCaptionImageResponseData>{

        val responseLiveData: MutableLiveData<ImgFlipCaptionImageResponseData> = MutableLiveData()
        val imgFlipRequest: Call<ImgFlipCaptionImageResponse> = this.api.captionImage(
            template_id = template_id,
            caption1 = caption,
            caption2 = caption,
            username = USERNAME,
            password = PASSWORD
        )
        Log.d(TAG, "Enqueuing a request to caption meme $template_id with: $caption")
        imgFlipRequest.enqueue(object : Callback<ImgFlipCaptionImageResponse> {

            override fun onFailure(call: Call<ImgFlipCaptionImageResponse>, t: Throwable) {
                Log.e(TAG, "Failed to caption ImgFlip meme template")
            }

            override fun onResponse(
                call: Call<ImgFlipCaptionImageResponse>,
                response: Response<ImgFlipCaptionImageResponse>
            ) {
                Log.d(TAG, "Response received from ImgFlip caption_image endpoint")

                val imgFlipCaptionImageResponse: ImgFlipCaptionImageResponse? = response.body()

                if (imgFlipCaptionImageResponse?.success == true) {
                    val imgFlipCaptionImageResponseData: ImgFlipCaptionImageResponseData =
                        imgFlipCaptionImageResponse.data
                    responseLiveData.value = imgFlipCaptionImageResponseData
                    Log.d(TAG, "Got new meme url: ${imgFlipCaptionImageResponseData.url}")
                } else {
                    Log.d(TAG,"Request to caption image has failed: {${imgFlipCaptionImageResponse?.error_message}}"
                    )}
            }
        })
        return responseLiveData
    }

    companion object {
        private  const val USERNAME = "GJSH"
        private  const val PASSWORD = "Memecreater123"
    }

}