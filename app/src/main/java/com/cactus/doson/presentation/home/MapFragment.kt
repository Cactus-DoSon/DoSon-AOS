package com.cactus.doson.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cactus.doson.DoSonApplication.Companion.retrofit
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants.ACCESS_CODE
import com.cactus.doson.common.Constants.NICK_NAME
import com.cactus.doson.common.Constants.PREFERENCES_NAME
import com.cactus.doson.data.Post
import com.cactus.doson.data.RetrofitAPI
import com.cactus.doson.databinding.FragmentMapBinding
import com.cactus.doson.presentation.dialog.BottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment: BaseFragment(R.layout.fragment_map) , OnMapReadyCallback {
    private var binding: FragmentMapBinding? = null
    val guestHouseMarker = Marker()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragmentBinding = FragmentMapBinding.bind(view)
        binding = mapFragmentBinding
        binding!!.mapView.getMapAsync(this@MapFragment)


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
            //moveToFragment(addPostFragment())
        }


    }



    override fun onMapReady(map: NaverMap) {
        Log.i("map", "ready")

        map.minZoom = 5.0
        map.maxZoom = 10.0

        var markerInfo = HashMap<String, String>()
        markerInfo["nickname"]= getStringData(NICK_NAME) as String
        markerInfo["accessCode"]= getStringData(ACCESS_CODE) as String


        guestHouseMarker.position = LatLng(33.3976832425448,126.243156415149)
        guestHouseMarker.tag = "guestHouse"
        guestHouseMarker.map = map
        guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_off)
        guestHouseMarker.width = changeDP(80)
        guestHouseMarker.height = changeDP(80)
        guestHouseMarker.zIndex = 0

        guestHouseMarker.onClickListener = Overlay.OnClickListener {

            val bottomSheet = BottomSheet()
            bottomSheet.show(childFragmentManager,bottomSheet.tag)

            val cameraUpdate = CameraUpdate.scrollTo(LatLng(guestHouseMarker.position.latitude!!, guestHouseMarker.position.longitude!!))
            map.moveCamera(cameraUpdate)

            guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_on)
            guestHouseMarker.zIndex = 100
            true
        }

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(guestHouseMarker.position.latitude!!, guestHouseMarker.position.longitude!!))
        map.moveCamera(cameraUpdate)



        /*
        retrofit.guestEnterAPI(markerInfo)?.enqueue(object : Callback<Post?>{
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {

                Log.i("guestEnter",response.body()?.success.toString())

                guestHouseMarker.position = LatLng(response.body()?.getResultList()?.getHouseInfoList()?.lat!!,response.body()?.getResultList()?.getHouseInfoList()?.lon!!)
                guestHouseMarker.tag = "guestHouse"
                guestHouseMarker.map = map
                guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_off)
                guestHouseMarker.width = changeDP(80)
                guestHouseMarker.height = changeDP(80)
                guestHouseMarker.zIndex = 0

                guestHouseMarker.onClickListener = Overlay.OnClickListener {



                    val bottomSheet = BottomSheet()
                    bottomSheet.show(childFragmentManager,bottomSheet.tag)
                    guestHouseMarker.icon = OverlayImage.fromResource(R.drawable.icon_80_home_on)
                    guestHouseMarker.zIndex = 100
                    true
                }

                val cameraUpdate = CameraUpdate.scrollTo(LatLng(guestHouseMarker.position.latitude!!, guestHouseMarker.position.longitude!!))
                map.moveCamera(cameraUpdate)

            }

            override fun onFailure(call: Call<Post?>, t: Throwable) {
                Log.i("guestEnter",t.stackTraceToString())
            }

        })

         */


    }

    private fun changeDP(value: Int): Int {
        var displayMetrics = requireContext().resources.displayMetrics
        return Math.round(value * displayMetrics.density)
    }

}