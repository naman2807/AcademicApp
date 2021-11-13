package com.example.academicapp.viewmodels

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academicapp.fragments.admin.AdminHomeFragment

class AdminViewModel: ViewModel() {
    private var _selectedFragment = MutableLiveData<Fragment>()
    private var _noticeImageUri = MutableLiveData<Uri?>()
    private var _functionImageUri = MutableLiveData<Uri?>()
    private var _pdfUri = MutableLiveData<Uri?>()

    val selectedFragment : LiveData<Fragment> get()  = _selectedFragment
    val noticeImageUri : MutableLiveData<Uri?> get() = _noticeImageUri
    val functionImageUri : MutableLiveData<Uri?> get() = _functionImageUri
    val pdfUri : MutableLiveData<Uri?> get() = _pdfUri

    init {
        _selectedFragment.value = AdminHomeFragment()
        _pdfUri.value = null
        _functionImageUri.value = null
        _noticeImageUri.value = null
    }

    fun selectFragment(fragment : Fragment){
        _selectedFragment.value = fragment
    }

    fun setNoticeImageUri(uri: Uri?){
        _noticeImageUri.value = uri
    }

    fun setFunctionImageUri(uri: Uri){
        _functionImageUri.value = uri
    }

    fun setPdfUri(uri: Uri){
        _pdfUri.value = uri
    }
}