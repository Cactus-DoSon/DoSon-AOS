package com.cactus.doson.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cactus.doson.R
import com.cactus.doson.databinding.ActivityMainBinding
import com.cactus.doson.presentation.login.LoginFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var loginFragment : Fragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDefaultFragment()
    }

    fun setDefaultFragment(){
        var transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container,loginFragment)
        transaction.commit()
    }

}
