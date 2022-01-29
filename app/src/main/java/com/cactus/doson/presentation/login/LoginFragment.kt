package com.cactus.doson.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants
import com.cactus.doson.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginFragment: BaseFragment(R.layout.fragment_login) {
    private var binding: FragmentLoginBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginFragmentBinding = FragmentLoginBinding.bind(view)
        binding = loginFragmentBinding

        initKakaoButton()
    }

    private fun initKakaoButton() {
        binding?.btKakaoLogin?.setOnClickListener {
            loginWithKakao()
        }
    }

    private fun loginWithKakao() {

        val callbackForAccount: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                val keyHash: String = Utility.getKeyHash(requireContext())
                Log.e(Constants.KAKAO_TAG, "로그인 실패 $keyHash", error)
            } else if (token != null) {
                Log.i(Constants.KAKAO_TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callbackForAccount)
            } else if (token != null) {
                Log.i(Constants.KAKAO_TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }

    }
}