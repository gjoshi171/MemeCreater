package com.ag.memecreater.api

import com.google.gson.annotations.SerializedName

class ImgFlipGetMemesResponseData {
    @SerializedName("memes")
    lateinit var templateItem: List<MemeTemplateItem>
}