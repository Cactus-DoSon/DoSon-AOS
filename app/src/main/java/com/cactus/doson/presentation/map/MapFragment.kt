package com.cactus.doson.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.data.response.enter.EnterResponse
import com.cactus.doson.databinding.FragmentMapBinding
import com.cactus.doson.presentation.dialog.BottomSheet
import com.cactus.doson.presentation.dialog.BottomSheet_spot
import com.cactus.doson.presentation.location_detail.LocationDetailFragment
import com.cactus.doson.presentation.post.add.AddPostFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage

class MapFragment(private val enterResponse: EnterResponse): BaseFragment(R.layout.fragment_map) , OnMapReadyCallback {
    private var binding: FragmentMapBinding? = null
    private lateinit var callback: OnBackPressedCallback
    val guestHouseMarker = Marker()

    var postId = ArrayList<Int>()
    var postLat = ArrayList<Double>()
    var postLon = ArrayList<Double>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragmentBinding = FragmentMapBinding.bind(view)
        binding = mapFragmentBinding
        binding!!.mapView.getMapAsync(this@MapFragment)

        //fragment popup
        val fm: FragmentManager = requireActivity().supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }


        var selected = "all"
        binding!!.allOnBtn.setOnClickListener{

            if(selected=="all"){
                binding!!.allOnBtn.setImageResource(R.drawable.btn_32_all_on)
            }
            else if(selected=="sight"){
                binding!!.sightBtn.setImageResource(R.drawable.btn_32_sights_off)
            }
            else if(selected=="restaurant"){
                binding!!.restaurantBtn.setImageResource(R.drawable.btn_32_restaurant_off)
            }
            else if(selected=="cafe"){
                binding!!.cafeBtn.setImageResource(R.drawable.btn_32_cafe_off)
            }

            selected = "all"
            binding!!.allOnBtn.setImageResource(R.drawable.btn_32_all_on)

        }
        binding!!.sightBtn.setOnClickListener{

            if(selected=="all"){
                binding!!.allOnBtn.setImageResource(R.drawable.btn_32_all_off)
            }
            else if(selected=="sight"){
                binding!!.sightBtn.setImageResource(R.drawable.btn_32_sights_on)
            }
            else if(selected=="restaurant"){
                binding!!.restaurantBtn.setImageResource(R.drawable.btn_32_restaurant_off)
            }
            else if(selected=="cafe"){
                binding!!.cafeBtn.setImageResource(R.drawable.btn_32_cafe_off)
            }

            selected = "sight"
            binding!!.sightBtn.setImageResource(R.drawable.btn_32_sights_on)

        }
        binding!!.restaurantBtn.setOnClickListener{

            if(selected=="all"){
                binding!!.allOnBtn.setImageResource(R.drawable.btn_32_all_off)
            }
            else if(selected=="sight"){
                binding!!.sightBtn.setImageResource(R.drawable.btn_32_sights_off)
            }
            else if(selected=="restaurant"){
                binding!!.restaurantBtn.setImageResource(R.drawable.btn_32_restaurant_on)
            }
            else if(selected=="cafe"){
                binding!!.cafeBtn.setImageResource(R.drawable.btn_32_cafe_off)
            }

            selected = "restaurant"
            binding!!.restaurantBtn.setImageResource(R.drawable.btn_32_restaurant_on)

        }
        binding!!.cafeBtn.setOnClickListener{

            if(selected=="all"){
                binding!!.allOnBtn.setImageResource(R.drawable.btn_32_all_off)
            }
            else if(selected=="sight"){
                binding!!.sightBtn.setImageResource(R.drawable.btn_32_sights_off)
            }
            else if(selected=="restaurant"){
                binding!!.restaurantBtn.setImageResource(R.drawable.btn_32_restaurant_off)
            }
            else if(selected=="cafe"){
                binding!!.cafeBtn.setImageResource(R.drawable.btn_32_cafe_on)
            }

            selected = "cafe"
            binding!!.cafeBtn.setImageResource(R.drawable.btn_32_cafe_on)

        }


        binding!!.mypageBtn.setOnClickListener{
            //moveToFragment(mypageFragment())
        }

        binding!!.addPostBtn.setOnClickListener{
            moveToFragment(AddPostFragment())
        }


    }



    override fun onMapReady(map: NaverMap) {
        Log.i("map", "ready")

        map.minZoom = 5.0
        map.maxZoom = 10.0


        //guestHouse
        guestHouseMarker.position = LatLng(enterResponse.result?.houseInfo?.houseLatitude?.toDouble()!!,enterResponse.result?.houseInfo?.houseLongitude?.toDouble()!!)
        guestHouseMarker.tag = "guestHouse"
        guestHouseMarker.map = map
        guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_off)
        guestHouseMarker.width = changeDP(80)
        guestHouseMarker.height = changeDP(80)
        guestHouseMarker.zIndex = 0

        guestHouseMarker.onClickListener = Overlay.OnClickListener {
            val bottomSheet = BottomSheet(
                callback = { guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_off) }
            )
            bottomSheet.show(childFragmentManager,bottomSheet.tag)

            val cameraUpdate = CameraUpdate.scrollTo(LatLng(guestHouseMarker.position.latitude!!, guestHouseMarker.position.longitude!!))
            map.moveCamera(cameraUpdate)

            guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_on)
            guestHouseMarker.zIndex = 100
            true
        }

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(guestHouseMarker.position.latitude!!, guestHouseMarker.position.longitude!!))
        map.moveCamera(cameraUpdate)

        //spot
        for (i in 0 until enterResponse.result?.housePostInfo?.size!!) {
            postId.add(enterResponse.result?.housePostInfo!![i].postId!!)
            postLat.add(enterResponse.result?.housePostInfo!![i].postLatitude!!.toDouble())
            postLon.add(enterResponse.result?.housePostInfo!![i].postLongitude!!.toDouble())

            val spotMarker = Marker()
            spotMarker.position = LatLng(postLat[i],postLon[i])
            spotMarker.tag = postId[i]
            spotMarker.map = map
            spotMarker.icon = OverlayImage.fromResource(R.drawable.icon_30_48_pick_off)
            spotMarker.width = changeDP(25)
            spotMarker.height = changeDP(40)
            spotMarker.zIndex = 0

            spotMarker.onClickListener = Overlay.OnClickListener {
                val bottomSheet = BottomSheet_spot (
                    callback = {
                        moveToFragment(LocationDetailFragment())
                    },
                    markerCallback = {
                        spotMarker.icon = OverlayImage.fromResource(R.drawable.icon_30_48_pick_off)
                    })
                bottomSheet.show(childFragmentManager,bottomSheet.tag)

                val cameraUpdate = CameraUpdate.scrollTo(LatLng(spotMarker.position.latitude!!, spotMarker.position.longitude!!))
                map.moveCamera(cameraUpdate)

                spotMarker.icon = OverlayImage.fromResource(R.drawable.icon_30_48_pick_on)
                spotMarker.zIndex = 100
                true
            }


        }



    }

    private fun changeDP(value: Int): Int {
        var displayMetrics = requireContext().resources.displayMetrics
        return Math.round(value * displayMetrics.density)
    }

}