package com.ag.memecreater

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ag.memecreater.api.ImgFlipCaptionImageResponseData
import com.ag.memecreater.api.ImgFlipExecutor
import com.ag.memecreater.api.MemeTemplateItem

class MemerViewModel :ViewModel() {
    private var templateIndex: Int = 0
    var captionMemeLiveData: LiveData<ImgFlipCaptionImageResponseData> = MutableLiveData<ImgFlipCaptionImageResponseData>()

    fun setTemplate(index: Int) {
        this.templateIndex = index
    }

    fun getTemplateIndex(): Int {
        return this.templateIndex
    }

    val memeTemplateLiveData: LiveData<List<MemeTemplateItem>>
    init {
        this.memeTemplateLiveData=ImgFlipExecutor().fetchTemplates()
    }

    fun increaseTemplateIndex(): Int{
        this.setTemplate(this.templateIndex + 1)
        this.keepTemplateIndexInBounds()
        return this.templateIndex

    }
    fun decreaseTemplateIndex(): Int{
        this.setTemplate(this.templateIndex - 1)
        this.keepTemplateIndexInBounds()
        return this.templateIndex
    }

    private fun keepTemplateIndexInBounds(){
        if(this.templateIndex >= (this.memeTemplateLiveData.value?.size ?: 0 )){
            this.templateIndex=0
        }
        else if(this.templateIndex< 0 && (this.memeTemplateLiveData.value?.size ?: 0) > 0){
            this.templateIndex = this.memeTemplateLiveData.value!!.size-1

        }
    }

    fun getCurrentMemeTemplate() : MemeTemplateItem?{
        if (this.memeTemplateLiveData.value != null){

            if(this.templateIndex>=0 && this.templateIndex <= this.memeTemplateLiveData.value!!.size){
                return this.memeTemplateLiveData.value!![this.templateIndex]
            }
        }
        return null
    }

    fun captionMeme(template_id: String, caption: String): LiveData<ImgFlipCaptionImageResponseData>{
        Log.d(TAG,"Received request to caption meme #$template_id with: $caption" )
        this.captionMemeLiveData= ImgFlipExecutor().captionImage(template_id, caption)
    return this.captionMemeLiveData
    }
}