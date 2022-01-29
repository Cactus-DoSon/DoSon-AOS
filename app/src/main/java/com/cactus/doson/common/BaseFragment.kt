package com.cactus.doson.common

import android.content.res.Resources
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cactus.doson.DoSonApplication


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {


    /** Util for all fragment **/
    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }



    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun getColorByResId(resId: Int) = resources.getColor(resId, null)

    fun getStringByResId(resId: Int): String = resources.getString(resId)

    fun saveStringData(pair: Pair<String, String>) {
        DoSonApplication.sharedPreferences
            .edit()
            .putString(
                pair.first,
                pair.second
            )
            .apply()
    }

    fun getStringData(key: String): String? {
        return DoSonApplication.sharedPreferences
            .getString(key, null)
    }

}
