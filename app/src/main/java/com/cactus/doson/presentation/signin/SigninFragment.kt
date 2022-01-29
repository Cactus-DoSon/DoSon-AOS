package com.cactus.doson.presentation.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants.ACCESS_CODE
import com.cactus.doson.common.Constants.NICK_NAME
import com.cactus.doson.databinding.FragmentSigninBinding
import com.cactus.doson.presentation.home.MapFragment

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
        binding?.tvCompleteSignin?.setOnClickListener {
            //TODO api 요청
            if (isCompleted()) {
                moveToFragment(MapFragment())
                saveStringData(Pair(NICK_NAME,binding?.etNickname.toString()))
                saveStringData(Pair(ACCESS_CODE,binding?.etInvitationCode.toString()))
            }
        }
    }

    private fun isCompleted(): Boolean = invitationCodeOk && nicknameOk



}