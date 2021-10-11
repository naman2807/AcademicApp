package com.example.academicapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.fragment.app.Fragment
import com.example.academicapp.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = binding.animation

        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 50; //You can manage the blinking time with this parameter
        animation.startOffset = 20
        animation.repeatMode = Animation.INFINITE
        animation.repeatCount = Animation.INFINITE
        textView.startAnimation(animation);

    }
}