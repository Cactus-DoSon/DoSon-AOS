package com.cactus.doson.presentation.location_detail

import android.os.Bundle
import android.view.View
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.databinding.FragmentLocationDetailBinding

class LocationDetailFragment: BaseFragment(R.layout.fragment_location_detail) {
    private var binding: FragmentLocationDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentLocationDetailBinding = FragmentLocationDetailBinding.bind(view)
        binding = fragmentLocationDetailBinding

    }
}