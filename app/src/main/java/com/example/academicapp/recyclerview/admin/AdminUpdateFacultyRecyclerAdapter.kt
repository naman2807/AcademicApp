package com.example.academicapp.recyclerview.admin

import android.annotation.SuppressLint
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academicapp.R
import com.example.academicapp.models.Faculty
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import de.hdodenhof.circleimageview.CircleImageView

class AdminUpdateFacultyRecyclerAdapter(options: FirebaseRecyclerOptions<Faculty>, private val onItemClicked: (Faculty) -> Unit) : FirebaseRecyclerAdapter<Faculty, AdminUpdateFacultyRecyclerAdapter.MyViewHolder>(
    options) {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val facultyName = view.findViewById<TextView>(R.id.recycler_view_name)
        val facultyDomain = view.findViewById<TextView>(R.id.recycler_view_domain)
        val facultyImage = view.findViewById<CircleImageView>(R.id.recycler_view_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_update_faculty_list_item, parent, false)
        return MyViewHolder(view = view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Faculty) {
        holder.facultyName.setText("${model.firstName} ${model.lastname}")
        holder.facultyDomain.setText(model.qualification + ": "+ model.domain)
        Glide.with(holder.facultyImage.context)
            .load(model.downloadImageUri).placeholder(R.drawable.profile)
            .circleCrop()
            .error(R.drawable.profile)
            .into(holder.facultyImage)
        holder.itemView.setOnClickListener {
            onItemClicked(model)
        }
    }
}