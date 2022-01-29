package com.cactus.doson.presentation.location_detail

import android.os.Bundle
import android.view.View
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.databinding.FragmentLocationDetailBinding
import com.cactus.doson.databinding.FragmentSigninBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class LocationDetailFragment: BaseFragment(R.layout.fragment_location_detail) {
    private var binding: FragmentLocationDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentLocationDetailBinding = FragmentLocationDetailBinding.bind(view)
        binding = fragmentLocationDetailBinding
        getData()
        initScrollView(fragmentLocationDetailBinding)
    }

    private fun getData() {
        //TODO 장소 상세정보 가져오기 api
    }

    private fun initSendButton() {
        binding?.tvSend?.setOnClickListener {
            //TODO 메시지 작성 화면으로 이동
        }
    }
    private fun initLikeButton() {
        binding?.ivLike?.setOnClickListener {
            //TODO 좋아요 api 호출, getData 재호출로 업데이트
        }
    }

    private fun initBackButton() {
        binding?.ivBack?.setOnClickListener {
            //TODO 뒤로가기
        }
    }

    private fun initScrollView(fragmentLocationDetailBinding: FragmentLocationDetailBinding) {
        OverScrollDecoratorHelper.setUpOverScroll(fragmentLocationDetailBinding.sv)
    }


}