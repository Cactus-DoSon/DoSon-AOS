package com.cactus.doson.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cactus.doson.databinding.DialogConfirmBinding

class ConfirmDialog(
    context: Context,

): Dialog(context) {
    private var binding: DialogConfirmBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialogConfirmBinding = DialogConfirmBinding.inflate(layoutInflater)
        binding = dialogConfirmBinding

    }

}