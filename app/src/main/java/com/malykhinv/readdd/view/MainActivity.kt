package com.malykhinv.readdd.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.malykhinv.readdd.R
import com.malykhinv.readdd.databinding.FragmentBookFlowBinding
import com.malykhinv.readdd.view.fragments.BookFlowFragment
import com.malykhinv.readdd.view.fragments.BookShelfFragment

class MainActivity : AppCompatActivity() {

    private val bookFlowFragment: BookFlowFragment = BookFlowFragment.newInstance()
    private val bookShelfFragment: BookShelfFragment = BookShelfFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
    }


}