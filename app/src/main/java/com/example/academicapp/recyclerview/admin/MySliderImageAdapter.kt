package com.example.academicapp.recyclerview.admin

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.academicapp.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class MySliderImageAdapter(context: Context): RecyclerView.Adapter<MySliderImageAdapter.MyViewHolder>(){

    val intArray = arrayListOf<Int>(
        R.drawable.image,
        R.drawable.image1,
        R.drawable.image3,
        R.drawable.image4
    )
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.imageSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageResource(intArray[position])
    }

    override fun getItemCount(): Int {
        return intArray.size
    }
}