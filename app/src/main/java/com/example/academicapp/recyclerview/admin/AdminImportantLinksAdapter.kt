package com.example.academicapp.recyclerview.admin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.academicapp.R

class AdminImportantLinksAdapter(private val context: Context, private val listItemsImage:MutableList<Int>, private val listText: MutableList<String>): RecyclerView.Adapter<AdminImportantLinksAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.important_link_image)
        val text = view.findViewById<TextView>(R.id.important_link_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.admin_important_links_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(listItemsImage[position])
        holder.text.setText(listText[position])
        holder.itemView.setOnClickListener {
            when(position){
                0 -> ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("https://glauniversity.in:8102/######")), null)

                1 -> ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("https://glauniversity.in:8085/")), null)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemsImage.size
    }
}