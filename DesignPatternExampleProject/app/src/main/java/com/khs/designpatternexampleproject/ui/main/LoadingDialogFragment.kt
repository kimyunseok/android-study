package com.khs.designpatternexampleproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khs.designpatternexampleproject.databinding.FragmentLoadingDialogBinding

class LoadingDialogFragment: DialogFragment() {

    lateinit var binding: FragmentLoadingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadingDialogBinding.inflate(inflater, container, false)

        isCancelable = false

        return binding.root
    }

}