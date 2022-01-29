package com.cactus.doson.presentation.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants
import com.cactus.doson.databinding.FragmentLoginBinding
import com.cactus.doson.presentation.home.MapFragment
import com.cactus.doson.presentation.signin.SigninFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginFragment: BaseFragment(R.layout.fragment_login) {
    private var binding: FragmentLoginBinding? = null
    private var myContext: FragmentActivity? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginFragmentBinding = FragmentLoginBinding.bind(view)
        binding = loginFragmentBinding

        initKakaoButton()
    }

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
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

                val transaction = myContext!!.supportFragmentManager.beginTransaction()
                val fragment: Fragment = SigninFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                transaction.replace(R.id.container, fragment)
                transaction.commit()

            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callbackForAccount)
            } else if (token != null) {
                Log.i(Constants.KAKAO_TAG, "로그인 성공 ${token.accessToken}")

                val transaction = myContext!!.supportFragmentManager.beginTransaction()
                val fragment: Fragment = SigninFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                transaction.replace(R.id.container, fragment)
                transaction.commit()

            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }

    }
}