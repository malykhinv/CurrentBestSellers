package com.malykhinv.readdd.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import com.malykhinv.readdd.R
import com.malykhinv.readdd.databinding.FragmentBookFlowBinding
import com.malykhinv.readdd.model.MainRepository
import com.malykhinv.readdd.model.RetrofitService
import com.malykhinv.readdd.view.adapters.CardQueueAdapter
import com.malykhinv.readdd.viewmodel.MainViewModel
import com.malykhinv.readdd.viewmodel.MainViewModelFactory
import com.yuyakaido.android.cardstackview.*


class BookFlowFragment : Fragment(), CardStackListener {

    private val retrofitService = RetrofitService.create()
    private val adapter = CardQueueAdapter()
    private val manager by lazy { CardStackLayoutManager(context, this) }
    lateinit var binding: FragmentBookFlowBinding
    lateinit var viewModel: MainViewModel

    companion object {

        fun newInstance(): BookFlowFragment {
            return BookFlowFragment()
        }
    }


    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        assignViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSavedBooks.setOnClickListener {goToShelf(it)}

        initializeCards()
        observeResponse()
    }


    // CardStackView

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        if (manager.topPosition == adapter.itemCount - 5) {
            // todo that's all
        }
    }

    override fun onCardRewound() {
        TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    private fun assignViewModel() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)
    }


    // Internal

    private fun initializeCards() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding.card.layoutManager = manager
        binding.card.adapter = adapter
        binding.card.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun setupButton() {

        // todo

//        val skip = findViewById<View>(R.id.skip_button)
//        skip.setOnClickListener {
//            val setting = SwipeAnimationSetting.Builder()
//                .setDirection(Direction.Left)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(AccelerateInterpolator())
//                .build()
//            manager.setSwipeAnimationSetting(setting)
//            binding.card.swipe()
//        }
//
//        val rewind = findViewById<View>(R.id.rewind_button)
//        rewind.setOnClickListener {
//            val setting = RewindAnimationSetting.Builder()
//                .setDirection(Direction.Bottom)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(DecelerateInterpolator())
//                .build()
//            manager.setRewindAnimationSetting(setting)
//            binding.card.rewind()
//        }
//
//        val like = findViewById<View>(R.id.like_button)
//        like.setOnClickListener {
//            val setting = SwipeAnimationSetting.Builder()
//                .setDirection(Direction.Right)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(AccelerateInterpolator())
//                .build()
//            manager.setSwipeAnimationSetting(setting)
//            binding.card.swipe()
//        }
    }

    private fun goToShelf(v: View) {
        val action = BookFlowFragmentDirections.actionGoToShelf()
        Navigation.findNavController(v).navigate(action)
    }

    private fun observeResponse() {
        viewModel.books.observe(
            viewLifecycleOwner,
            {
                adapter.setListOfBooks(it)
            }
        )
        viewModel.errorMessage.observe(
            viewLifecycleOwner,
            {
                Toast.makeText(context, getString(R.string.error_loading_books), Toast.LENGTH_SHORT).show()
            }
        )
        viewModel.getBooks()
    }

}