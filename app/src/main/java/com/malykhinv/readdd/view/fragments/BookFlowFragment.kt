package com.malykhinv.readdd.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.malykhinv.readdd.databinding.FragmentBookFlowBinding
import androidx.navigation.Navigation


class BookFlowFragment : Fragment() {

    private var binding: FragmentBookFlowBinding? = null

    companion object {

        fun newInstance(): BookFlowFragment {
            return BookFlowFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookFlowBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivSavedBooks?.setOnClickListener {goToShelf(it)}
    }

    private fun goToShelf(v: View) {
        val action = BookFlowFragmentDirections.actionGoToShelf()
        Navigation.findNavController(v).navigate(action)
    }
}