package com.malykhinv.currentbestsellers.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.malykhinv.currentbestsellers.R
import com.malykhinv.currentbestsellers.presentation.fragments.BookCardsFragment
import com.malykhinv.currentbestsellers.presentation.fragments.SavedBooksFragment

class MainActivity : AppCompatActivity() {

    private val bookCardsFragment: BookCardsFragment = BookCardsFragment.newInstance()
    private val savedBooksFragment: SavedBooksFragment = SavedBooksFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
    }


}