package com.malykhinv.currentbestsellers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.malykhinv.currentbestsellers.R
import com.malykhinv.currentbestsellers.view.fragments.BookCardsFragment
import com.malykhinv.currentbestsellers.view.fragments.BookShelfFragment

class MainActivity : AppCompatActivity() {

    private val bookCardsFragment: BookCardsFragment = BookCardsFragment.newInstance()
    private val bookShelfFragment: BookShelfFragment = BookShelfFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
    }


}