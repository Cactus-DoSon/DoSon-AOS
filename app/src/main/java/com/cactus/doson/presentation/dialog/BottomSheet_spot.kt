package com.cactus.doson.presentation.dialog

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.cactus.doson.R
import com.cactus.doson.presentation.home.MapFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.naver.maps.map.overlay.Marker

class BottomSheet_spot(
    private val callback: (Boolean) -> Unit,
    private val markerCallback : (Boolean)-> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_spot_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.findViewById<ImageButton>(R.id.closeBtn)?.setOnClickListener {
            dismiss()
        }

        view?.findViewById<ConstraintLayout>(R.id.detailBtn)?.setOnClickListener{
            callback(true)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        markerCallback(true)
    }



}