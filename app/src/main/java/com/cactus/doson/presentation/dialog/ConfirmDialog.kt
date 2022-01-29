package com.cactus.doson.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cactus.doson.databinding.DialogConfirmBinding

class ConfirmDialog(
    context: Context,
    private val what: String,
    private val callback: (Boolean) -> Unit
): Dialog(context) {
    private var binding: DialogConfirmBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialogConfirmBinding = DialogConfirmBinding.inflate(layoutInflater)
        binding = dialogConfirmBinding
        initTitleTextView()
        initCancelTextView()
        initConfirmTextView()
    }

    private fun initTitleTextView() {
        when(what) {
            "post" -> {
                binding?.tvTitle?.text = "게시글을 삭제하시겠습니까?"
            }
            "message" -> {
                binding?.tvTitle?.text = "메세지를 삭제하시겠습니까?"
            }
        }
    }

    private fun initCancelTextView() {
        binding?.tvCancel?.setOnClickListener {
            callback(false)
        }
    }

    private fun initConfirmTextView() {
        binding?.tvConfirm?.setOnClickListener {
            callback(true)
        }
    }

}