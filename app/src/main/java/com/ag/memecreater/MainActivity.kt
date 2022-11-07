package com.ag.memecreater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up the meme template fragment

        val memeTemplateFragment =this.supportFragmentManager.findFragmentById(R.id.meme_template_fragment_container)

        if(memeTemplateFragment== null){
            val frag = MemeTemplateFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.meme_template_fragment_container, frag)
                .commit()}

        //set up the rendered meme fragment template

        val renderedMemeFragment= this.supportFragmentManager.findFragmentById(R.id.rendered_meme_container)
        if(renderedMemeFragment== null){
            val frag = RenderedMemeFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.rendered_meme_container,frag)
                .commit()}

    }
}