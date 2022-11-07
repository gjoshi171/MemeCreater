package com.ag.memecreater

import androidx.lifecycle.ViewModel

class MemerViewModel :ViewModel() {
    private var templateIndex: Int=0

    fun setTemplate(index: Int){
        this.templateIndex= index
    }

    fun getTemplate(): Int{
        return this.templateIndex
    }
}