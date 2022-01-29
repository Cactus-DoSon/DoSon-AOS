package com.cactus.doson.presentation.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.databinding.FragmentSigninBinding

class SigninFragment: BaseFragment(R.layout.fragment_signin) {
    private var binding: FragmentSigninBinding? = null


    private var invitationCodeOk = false
    private var nicknameOk = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSigninBinding = FragmentSigninBinding.bind(view)
        binding = fragmentSigninBinding


        initEditTexts()
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

            it.etNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val len = p0.toString().length
                    nicknameOk = len in 1..8
                    updateCompleteButton()
                }
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun updateCompleteButton() {
        if (invitationCodeOk && nicknameOk) {
            binding?.tvCompleteSignin?.setBackgroundResource(R.color.orange)
        } else {
            binding?.tvCompleteSignin?.setBackgroundResource(R.color.gray_2)
        }
    }


}