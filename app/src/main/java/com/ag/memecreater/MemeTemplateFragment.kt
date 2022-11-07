package com.ag.memecreater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class MemeTemplateFragment : Fragment() {

    private  lateinit var memerViewModel: MemerViewModel
    private lateinit var memeTemplateImage: ImageView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var memeTemplateIndexView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.memerViewModel= ViewModelProviders.of(this).get(MemerViewModel::class.java)

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

        return view
    }
}