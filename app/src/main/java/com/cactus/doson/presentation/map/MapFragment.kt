package com.cactus.doson.presentation.map

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants.RETROFIT_TAG
import com.cactus.doson.common.util.printLog
import com.cactus.doson.data.response.map_category.MapCategoryResponse
import com.cactus.doson.databinding.FragmentMapBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment: BaseFragment(R.layout.fragment_map) , OnMapReadyCallback {
    private var binding: FragmentMapBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragmentBinding = FragmentMapBinding.bind(view)
        binding = mapFragmentBinding

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
        Log.i("Naver map", "ready")

        val uiSettings = map.uiSettings
        uiSettings.isLocationButtonEnabled = true


    }





}