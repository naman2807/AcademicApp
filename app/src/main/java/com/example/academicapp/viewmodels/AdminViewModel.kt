package com.example.academicapp.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academicapp.fragments.admin.AdminHomeFragment

class AdminViewModel: ViewModel() {
    private var _selectedFragment = MutableLiveData<Fragment>()
    val selectedFragment : LiveData<Fragment> get()  = _selectedFragment

    init {
        _selectedFragment.value = AdminHomeFragment()
    }

    fun selectFragment(fragment : Fragment){
        _selectedFragment.value = fragment
    }
}