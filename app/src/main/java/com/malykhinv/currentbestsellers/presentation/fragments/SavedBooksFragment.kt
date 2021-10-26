package com.malykhinv.currentbestsellers.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.malykhinv.currentbestsellers.databinding.FragmentBookShelfBinding

class SavedBooksFragment : Fragment() {

    private var binding: FragmentBookShelfBinding? = null

    companion object {

        fun newInstance(): SavedBooksFragment {
            return SavedBooksFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookShelfBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}