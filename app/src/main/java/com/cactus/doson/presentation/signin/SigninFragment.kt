package com.cactus.doson.presentation.signin

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants
import com.cactus.doson.common.util.printLog
import com.cactus.doson.data.body.EnterBody
import com.cactus.doson.data.response.enter.EnterResponse
import com.cactus.doson.databinding.FragmentSigninBinding
import com.cactus.doson.presentation.map.MapFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninFragment: BaseFragment(R.layout.fragment_signin) {
    private var binding: FragmentSigninBinding? = null


    private var invitationCodeOk = false
    private var nicknameOk = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSigninBinding = FragmentSigninBinding.bind(view)
        binding = fragmentSigninBinding


        initEditTexts()
        initCompleteButton()
    }

    private fun initEditTexts() {
        binding?.let {
            it.etInvitationCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val len = p0.toString().length
                    Log.d("adsf", p0.toString())
                    invitationCodeOk = len == 8
                    updateCompleteButton()
                }
                override fun afterTextChanged(p0: Editable?) {}
            })
            it.etInvitationCode.setOnFocusChangeListener { view, isFocused ->
                if (isFocused) {
                    it.etInvitationCode.setBackgroundResource(R.drawable.bg_signin_et_focus)
                } else {
                    it.etInvitationCode.setBackgroundResource(R.drawable.bg_signin_et)
                }
            }

            it.etNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val len = p0.toString().length
                    nicknameOk = len in 1..8
                    updateCompleteButton()
                }
                override fun afterTextChanged(p0: Editable?) {}
            })

            it.etNickname.setOnFocusChangeListener { view, isFocused ->
                if (isFocused) {
                    it.etNickname.setBackgroundResource(R.drawable.bg_signin_et_focus)
                } else {
                    it.etNickname.setBackgroundResource(R.drawable.bg_signin_et)
                }
            }

        }
    }

    private fun updateCompleteButton() {
        if (isCompleted()) {
            binding?.tvCompleteSignin?.setBackgroundColor(getColorByResId(R.color.orange))
        } else {
            binding?.tvCompleteSignin?.setBackgroundColor(getColorByResId(R.color.gray_2))
        }
    }


    private fun initCompleteButton() {
        binding?.apply {
            tvCompleteSignin.setOnClickListener {
                if (isCompleted()) {
                    enterGuestHouse(
                        accessCode = etInvitationCode.text.toString(),
                        nickname = etNickname.text.toString()
                    )
                }
            }
        }

    }

    private fun enterGuestHouse(accessCode: String, nickname: String) {
        DoSonApplication.retrofit.enterApi(
            EnterBody(
            accessCode = accessCode,
            nickname = nickname
        )
        )?.enqueue(object : Callback<EnterResponse> {
            override fun onResponse(call: Call<EnterResponse>, response: Response<EnterResponse>) {
                printLog(Constants.RETROFIT_TAG, response.body().toString())
                moveToFragment(MapFragment())
            }

            override fun onFailure(call: Call<EnterResponse>, t: Throwable) {
                printLog(Constants.RETROFIT_TAG, t.toString())
            }
        })
    }

    private fun isCompleted(): Boolean = invitationCodeOk && nicknameOk



}