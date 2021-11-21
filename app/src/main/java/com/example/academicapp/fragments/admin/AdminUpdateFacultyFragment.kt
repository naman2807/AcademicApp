package com.example.academicapp.fragments.admin

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academicapp.R
import com.example.academicapp.databinding.AdminUpdateFacultyFragmentBinding
import com.example.academicapp.models.Faculty
import com.example.academicapp.recyclerview.admin.AdminUpdateFacultyRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class AdminUpdateFacultyFragment: Fragment() {
    private lateinit var binding: AdminUpdateFacultyFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var faculty: Faculty
    private lateinit var dialog: Dialog
    private lateinit var adapter: AdminUpdateFacultyRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminUpdateFacultyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.updateFacultyRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        val options: FirebaseRecyclerOptions<Faculty> = FirebaseRecyclerOptions.Builder<Faculty>()
            .setQuery(FirebaseDatabase.getInstance().getReference("Faculties"), Faculty::class.java)
            .build()

        adapter = AdminUpdateFacultyRecyclerAdapter(options = options)
        recyclerView.adapter = adapter
    }

    private fun getFaculties(): MutableList<Faculty>{
        val facultiesList: MutableList<Faculty>  = arrayListOf()
        showProgressBar()
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children){
                    Log.e("faculty", dataSnapshot.child("firstName").getValue().toString())
                    faculty = dataSnapshot.getValue(Faculty::class.java)!!
                    Log.e("faculty", faculty.address.toString())
                    facultiesList.add(faculty)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
            }
        })
        Log.e("Faculty", facultiesList.size.toString())
        hideProgressBar()
        return facultiesList
    }

    private fun showProgressBar(){
        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}