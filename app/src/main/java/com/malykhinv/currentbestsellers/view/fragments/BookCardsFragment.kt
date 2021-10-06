package com.malykhinv.currentbestsellers.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.malykhinv.currentbestsellers.R
import com.malykhinv.currentbestsellers.databinding.FragmentBookCardsBinding
import com.malykhinv.currentbestsellers.model.MainRepository
import com.malykhinv.currentbestsellers.model.RetrofitService
import com.malykhinv.currentbestsellers.view.adapters.CardStackAdapter
import com.malykhinv.currentbestsellers.viewmodel.MainViewModel
import com.malykhinv.currentbestsellers.viewmodel.MainViewModelFactory
import com.yuyakaido.android.cardstackview.*


class BookCardsFragment : Fragment(), CardStackListener {

    private val retrofitService = RetrofitService.create()
    private val adapter = CardStackAdapter()
    private val manager by lazy { CardStackLayoutManager(context, this) }
    lateinit var binding: FragmentBookCardsBinding
    lateinit var viewModel: MainViewModel

    companion object {

        fun newInstance(): BookCardsFragment {
            return BookCardsFragment()
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

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_book_cards, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSavedBooks.setOnClickListener {goToShelf(it)}

        initializeCards()
        observeResponse()
        observeCurrentBook()
        observeCovers()
    }


    // CardStackView

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
        viewModel.onCardAppeared(position)
        setBackgroundImage(viewModel.getCovers().value?.get(position))
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    private fun assignViewModel() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService), requireActivity().application)).get(MainViewModel::class.java)
    }


    // Internal

    private fun initializeCards() {
        manager.setStackFrom(StackFrom.Right)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(0.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding.card.layoutManager = manager
        binding.card.adapter = adapter
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
//
    }

    private fun goToShelf(v: View) {
        val action = BookCardsFragmentDirections.actionGoToShelf()
        Navigation.findNavController(v).navigate(action)
    }

    private fun observeResponse() {
        viewModel.getApiResponse().observe(
            viewLifecycleOwner,
            {
                adapter.setListOfBooks(it)
            }
        )
        viewModel.getErrorMessage().observe(
            viewLifecycleOwner,
            {
                Toast.makeText(context, getString(R.string.error_loading_books), Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun observeCurrentBook() {
        viewModel.getCurrentBook().observe(
            viewLifecycleOwner,
            {
                binding.book = it
            }
        )
    }

    private fun observeCovers() {
        viewModel.getCovers().observe(
            viewLifecycleOwner,
            {
                adapter.setCovers(it)
                setBackgroundImage(it[0])
            }
        )
    }

    private fun setBackgroundImage(drawable: Drawable?) {
        binding.layoutCore.background = drawable
    }

}