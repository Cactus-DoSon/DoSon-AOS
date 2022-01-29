package com.cactus.doson.presentation.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.databinding.FragmentLoginBinding
import com.cactus.doson.databinding.FragmentMapBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MapFragment: BaseFragment(R.layout.fragment_map) , OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null
    private var myContext: FragmentActivity? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragmentBinding = FragmentMapBinding.bind(view)
        binding = mapFragmentBinding
    }

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    override fun onMapReady(p0: NaverMap) {
        Log.i("Naver map", "ready")
    }


}