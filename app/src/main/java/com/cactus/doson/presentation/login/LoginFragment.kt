package com.cactus.doson.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants.JWT
import com.cactus.doson.common.Constants.RETROFIT_TAG
import com.cactus.doson.common.util.printLog
import com.cactus.doson.data.body.LoginBody
import com.cactus.doson.data.response.LoginResponse
import com.cactus.doson.data.response.map_category.MapCategoryResponse
import com.cactus.doson.databinding.FragmentLoginBinding
import com.cactus.doson.presentation.signin.SigninFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment: BaseFragment(R.layout.fragment_login) {
    private var binding: FragmentLoginBinding? = null
    private lateinit var callback: OnBackPressedCallback

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
            } else if (token != null) {
                requestEmailAgreement(token)
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callbackForAccount)
            } else if (token != null) {
                requestEmailAgreement(token)
                postLoginBody(token)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }

    }
    private fun postLoginBody(token: OAuthToken) {


        DoSonApplication.retrofit.loginApi(LoginBody(token.accessToken))?.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                printLog(RETROFIT_TAG, response.body().toString())
                printLog(RETROFIT_TAG, response.message())
                moveToFragment(SigninFragment())
                response.body()?.result?.let { r ->
                    r.jwt?.let { j ->
                        saveStringData(Pair(JWT, j))
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                printLog(RETROFIT_TAG, t.toString())
            }
        })
    }

    private fun requestEmailAgreement(token: OAuthToken) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(RETROFIT_TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                var scopes = mutableListOf<String>()

                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }


                if (scopes.count() > 0) {
                    Log.d(RETROFIT_TAG, "사용자에게 추가 동의를 받아야 합니다.")

                    UserApiClient.instance.loginWithNewScopes(requireContext(), scopes) { token, error ->
                        if (error != null) {
                            Log.e(RETROFIT_TAG, "사용자 추가 동의 실패", error)
                        } else {
                            Log.d(RETROFIT_TAG, "allowed scopes: ${token!!.scopes}")

                            // 사용자 정보 재요청
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(RETROFIT_TAG, "사용자 정보 요청 실패", error)
                                }
                                else if (user != null) {
                                    Log.i(RETROFIT_TAG, "사용자 정보 요청 성공")
                                    postLoginBody(token)
                                }
                            }
                        }
                    }
                } else {
                    postLoginBody(token)
                }
            }
        }
    }


    /** api 모음 (적재적소 쓰자!) **/
    private fun getMapDataByCategory() {
        DoSonApplication.retrofit.searchGuestAPI(1, "cafe")?.enqueue(object : Callback<MapCategoryResponse>{
            override fun onResponse(
                call: Call<MapCategoryResponse>,
                response: Response<MapCategoryResponse>
            ) {
                Log.d(RETROFIT_TAG, response.body().toString())
            }

            override fun onFailure(call: Call<MapCategoryResponse>, t: Throwable) {
                printLog(RETROFIT_TAG, t.toString())
            }
        })
    }



}