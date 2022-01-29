package com.cactus.doson.common

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.presentation.signin.SigninFragment


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    private var myContext: FragmentActivity? = null


    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }


    /** Util for all fragment **/
    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun moveToFragment(fragment: Fragment) {
        val transaction = myContext!!.supportFragmentManager.beginTransaction()
        val fragment: Fragment = SigninFragment()
        val bundle = Bundle()
        fragment.arguments = bundle
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    fun moveToFragmentWithBundle(fragment: Fragment, bundle: Bundle) {
        val transaction = myContext!!.supportFragmentManager.beginTransaction()
        val fragment: Fragment = SigninFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.container, fragment)
        transaction.commit()
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
