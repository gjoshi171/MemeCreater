package com.ag.memecreater

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso

class RenderedMemeFragment: Fragment() {
    private  lateinit var memerViewModel: MemerViewModel
    private lateinit var captionInput: TextView
    private lateinit var captionButton: Button
    private lateinit var captionedMemeImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //this.memerViewModel= ViewModelProviders.of(this).get(MemerViewModel::class.java)

        //this way the context of our ViewModel will be the main activity, rather than an individual fragment
        this.memerViewModel= ViewModelProviders.of(this.requireActivity()).get(MemerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_rendered_meme, container, false)

        //connect our views
        this.captionInput = view.findViewById(R.id.caption_input)
        this.captionButton = view.findViewById(R.id.caption_button)
        this.captionedMemeImage = view.findViewById(R.id.caption_meme)

        this.captionButton.setOnClickListener {
            Log.v(TAG, "Caption button clicked! " )

            val text = this.captionInput.text
            val memeTemplate =  this.memerViewModel.getCurrentMemeTemplate()

            if(text == null){
                Log.e(TAG, "cannot caption meme because the caption text is null! ")
            }

            else if (memeTemplate == null){
                Log.e(TAG, "cannot caption meme because there is no selected template!")
            }
            else{
                Log.v(TAG, "Asking the MemerViewModel to caption a meme: $text (${memeTemplate})" )
                this.memerViewModel.captionMeme(
                    template_id = memeTemplate.id,
                    caption = text.toString())
                    .observe(this.viewLifecycleOwner, Observer{ captionMemeResponseData ->
                    Log.d(TAG,"Fragment has noticed a meme has been created: $captionMemeResponseData")
                    Picasso.get()
                        .load(captionMemeResponseData.url)
                        .into(this.captionedMemeImage)
                })
            }
        }
        return view
    }
}