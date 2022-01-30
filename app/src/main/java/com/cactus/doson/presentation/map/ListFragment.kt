package com.cactus.doson.presentation.map

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants
import com.cactus.doson.common.util.printLog
import com.cactus.doson.data.response.post.PostDetailResponse
import com.cactus.doson.databinding.FragmentListBinding
import com.cactus.doson.databinding.FragmentMapBinding
import com.cactus.doson.presentation.adapter.listAdapter
import com.cactus.doson.presentation.location_detail.LocationDetailFragment
import com.cactus.doson.presentation.post.add.AddPostFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment :BaseFragment(R.layout.fragment_list){
    private var binding: FragmentListBinding? = null

    var postId = ArrayList<Int>()
    var postTitle  = ArrayList<String>()
    var postDetail = ArrayList<String>()
    var postDate = ArrayList<String>()
    var postImg = ArrayList<Uri>()
    val adapter = listAdapter(postTitle,postDetail,postDate,postImg)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listFragmentBinding = FragmentListBinding.bind(view)
        binding = listFragmentBinding


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



        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.callback = { pos ->
                getPostById(postId[pos])

            }
        }
    }

    // 6. 글 상세보기
    private fun getPostById(postId: Int) {
        DoSonApplication.retrofit.getPostDetail(postId)?.enqueue(object :
            Callback<PostDetailResponse> {
            override fun onResponse(
                call: Call<PostDetailResponse>,
                response: Response<PostDetailResponse>
            ) {
                moveToFragment(LocationDetailFragment())
            }

            override fun onFailure(call: Call<PostDetailResponse>, t: Throwable) {
                printLog(Constants.RETROFIT_TAG, t.toString())
            }
        })
    }
}