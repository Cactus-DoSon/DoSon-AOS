package com.cactus.doson.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants.JWT
import com.cactus.doson.common.Constants.RETROFIT_TAG
import com.cactus.doson.common.util.printLog
import com.cactus.doson.data.body.LoginBody
import com.cactus.doson.data.response.LoginResponse
import com.cactus.doson.data.response.all_list.AllListResponse
import com.cactus.doson.data.response.bottom_data.guest_house.GeustHouseResponse
import com.cactus.doson.data.response.bottom_data.non_guest_house.NonGeustHouseResponse
import com.cactus.doson.data.response.map_category.MapCategoryResponse
import com.cactus.doson.data.response.post.PostDetailResponse
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginFragmentBinding = FragmentLoginBinding.bind(view)
        binding = loginFragmentBinding

        initKakaoButton()
        getAllList()
        getPostId(2)
        getNonGuestHouseData(2)
        getGuestHouseData(1)
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
    // 3. 지도 카테고리
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

    // 5. 전체 리스트
    private fun getAllList() {
        DoSonApplication.retrofit.getAllList(1)?.enqueue(object : Callback<AllListResponse> {
            override fun onResponse(
                call: Call<AllListResponse>,
                response: Response<AllListResponse>
            ) {
                Log.d(RETROFIT_TAG, response.body().toString())
            }

            override fun onFailure(call: Call<AllListResponse>, t: Throwable) {
                printLog(RETROFIT_TAG, t.toString())
            }
        })
    }

    // 6. 글 상세보기
    private fun getPostId(postId: Int) {
        DoSonApplication.retrofit.getPostDetail(postId)?.enqueue(object : Callback<PostDetailResponse> {
            override fun onResponse(
                call: Call<PostDetailResponse>,
                response: Response<PostDetailResponse>
            ) {
                Log.d(RETROFIT_TAG, response.body().toString())
            }

            override fun onFailure(call: Call<PostDetailResponse>, t: Throwable) {
                printLog(RETROFIT_TAG, t.toString())
            }
        })
    }

    // 7. 지도 스팟 찍었을 때 맵 바텀시트 데이터
    private fun getNonGuestHouseData(postId: Int) {
        DoSonApplication.retrofit.getBottomNonGuestHouseData(postId)?.enqueue(object : Callback<GeustHouseResponse>{
            override fun onResponse(
                call: Call<GeustHouseResponse>,
                response: Response<GeustHouseResponse>
            ) {
                Log.d(RETROFIT_TAG, response.body().toString())
            }

            override fun onFailure(call: Call<GeustHouseResponse>, t: Throwable) {
                printLog(RETROFIT_TAG, t.toString())
            }
        })
    }

    // 8. 지도 스팟 찍었을 때 게스트하우스 정보
    private fun getGuestHouseData(houseId: Int) {
        DoSonApplication.retrofit.getBottomGuestHouseData(houseId)?.enqueue(object : Callback<NonGeustHouseResponse> {
            override fun onResponse(
                call: Call<NonGeustHouseResponse>,
                response: Response<NonGeustHouseResponse>
            ) {
                Log.d(RETROFIT_TAG, response.body().toString())
            }

            override fun onFailure(call: Call<NonGeustHouseResponse>, t: Throwable) {
                printLog(RETROFIT_TAG, t.toString())
            }
        })
    }



}