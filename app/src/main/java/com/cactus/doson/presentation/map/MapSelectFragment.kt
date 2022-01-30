package com.cactus.doson.presentation.map

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.databinding.FragmentMapBinding
import com.cactus.doson.databinding.FragmentSelectLocationBinding
import com.cactus.doson.presentation.post.add.AddPostFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class MapSelectFragment : BaseFragment(R.layout.fragment_select_location) , OnMapReadyCallback {
    private var binding: FragmentSelectLocationBinding? = null
    private lateinit var callback: OnBackPressedCallback
    private var selectMarker : Marker ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapSelectFragment = FragmentSelectLocationBinding.bind(view)
        binding = mapSelectFragment
        binding!!.mapView.getMapAsync(this@MapSelectFragment)

        binding!!.selectBtn.setOnClickListener{
            if(selectMarker!=null){
                val bundle = Bundle()
                bundle.putDouble("selected_lat",selectMarker!!.position.latitude)
                bundle.putDouble("selected_lon",selectMarker!!.position.longitude)
                moveToFragmentWithBundle(AddPostFragment(),bundle)
                val fm: FragmentManager = requireActivity().supportFragmentManager
                fm.popBackStack()
                fm.popBackStack()
            }
        }

        binding!!.cancelBtn.setOnClickListener{
            moveToFragment(AddPostFragment())
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStack()
            fm.popBackStack()
        }
}

    override fun onMapReady(map: NaverMap) {
        Log.i("map", "ready")

        map.minZoom = 5.0
        map.maxZoom = 10.0

        //제주 중심부
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(33.36120572222292, 126.53264169265034))
        map.moveCamera(cameraUpdate)

        map.setOnMapClickListener{ pointF: PointF, latLng: LatLng ->
            if(selectMarker!=null){
                selectMarker!!.map = null
                selectMarker = null
            }
            else{
                val tempMarker = Marker()
                tempMarker.position = latLng
                tempMarker.tag = "selected"
                tempMarker.map = map
                tempMarker.icon = OverlayImage.fromResource(R.drawable.icon_30_48_pick_on)
                tempMarker.width = changeDP(25)
                tempMarker.height = changeDP(40)
                tempMarker.zIndex = 0
            }
        }


    }

    private fun changeDP(value: Int): Int {
        var displayMetrics = requireContext().resources.displayMetrics
        return Math.round(value * displayMetrics.density)
    }

}