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

class MemeTemplateFragment : Fragment() {

    private  lateinit var memerViewModel: MemerViewModel
    private lateinit var memeTemplateImage: ImageView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var memeTemplateIndexView: TextView

    private lateinit var modelView : MemerViewModel

    fun updateToCurrentMemeTemplate(){
       this.memeTemplateIndexView.text = this.memerViewModel.getTemplateIndex().toString()

        val meme = this.memerViewModel.getCurrentMemeTemplate()
        if(meme!= null){
            Log.v(TAG, "Meme Selected: $meme")
            Picasso.get()
                .load(meme.url)
                .into(this.memeTemplateImage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.memerViewModel= ViewModelProviders.of(this.requireActivity()).get(MemerViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate the layout
        val view = inflater.inflate(R.layout.fragment_meme_template,container,false)

        //connect our views
        this.memeTemplateImage = view.findViewById(R.id.main_image_view)
        this.prevButton = view.findViewById(R.id.main_previous)
        this.nextButton= view.findViewById(R.id.main_next)
        this.memeTemplateIndexView= view.findViewById(R.id.main_text)

        this.prevButton.setOnClickListener {
            this.memerViewModel.decreaseTemplateIndex()
            this.updateToCurrentMemeTemplate()
        }

        this.nextButton.setOnClickListener {
            this.memerViewModel.increaseTemplateIndex()
            this.updateToCurrentMemeTemplate()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.memerViewModel.memeTemplateLiveData.observe(
            this.viewLifecycleOwner, Observer { memeTemplates->
                Log.d(TAG, "ViewModel has noticed new meme templates: $memeTemplates")
                this.updateToCurrentMemeTemplate()
            }
        )
    }
}