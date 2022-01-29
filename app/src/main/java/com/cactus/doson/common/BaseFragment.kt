package com.cactus.doson.common

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.cactus.doson.DoSonApplication


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    fun moveToDirections(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    fun popOneDirections() {
        findNavController().popBackStack()
    }

    fun popUntilDirections(id: Int) {
        findNavController().popBackStack(id, true)
    }


    private fun observeSaveNavigationDataEvent(key: String) {
        getNavigationResult(key)?.observe(viewLifecycleOwner) { pair ->
            // 로직처리
        }
    }

    protected fun getNavigationResult(key: String): MutableLiveData<String>? {
        return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)
    }

    private fun saveNavigationResult(key: String, result: String) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }


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
