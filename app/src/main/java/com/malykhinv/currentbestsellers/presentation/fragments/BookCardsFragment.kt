package com.malykhinv.currentbestsellers.presentation.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.malykhinv.currentbestsellers.R
import com.malykhinv.currentbestsellers.databinding.FragmentBookCardsBinding
import com.malykhinv.currentbestsellers.data.repository.BooksRepositoryImpl
import com.malykhinv.currentbestsellers.data.source.RetrofitService
import com.malykhinv.currentbestsellers.presentation.adapters.CardStackAdapter
import com.malykhinv.currentbestsellers.presentation.viewmodel.BooksViewModel
import com.malykhinv.currentbestsellers.presentation.viewmodel.BooksViewModelFactory
import com.yuyakaido.android.cardstackview.*
import jp.wasabeef.blurry.Blurry


class BookCardsFragment : Fragment(), CardStackListener {

    private var categoryPath: String? = null
    private val retrofitService = RetrofitService.create()
    private val adapter = CardStackAdapter()
    private val manager by lazy { CardStackLayoutManager(context, this) }
    private var previousBookRankDynamics = 0
    private var numberOfBooks = 0
    lateinit var binding: FragmentBookCardsBinding
    lateinit var viewModel: BooksViewModel

    companion object {

        fun newInstance(): BookCardsFragment {
            return BookCardsFragment()
        }

        private const val ANIMATION_DURATION_SHORT: Long = 300L
        private const val ANIMATION_DURATION_LONG: Long = 1500L
        private const val BLUR_LEVEL: Int = 10
        private const val SAMPLING_LEVEL: Int = 8
        private const val BACKGROUND_SATURATION: Float = 0.0f
        private const val CARD_TRANSLATION_INTERVAL: Float = 180.0f
        private const val COUNT_OF_VISIBLE_CARDS: Int = 2
        private const val NEXT_CARD_INITIAL_SCALE: Float = 1.0f
        private const val CARD_SWIPE_THRESHOLD: Float = 0.3f
        private const val CARD_SWIPE_ANGLE: Float = -15.0f
    }


    // LIFECYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            categoryPath = it.getString("CATEGORY_PATH")
        }

        assignViewModel()
        observeViewModel()
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

        assignControls()
        initializeCards()
    }

    private fun assignViewModel() {
        viewModel = ViewModelProvider(this, BooksViewModelFactory(BooksRepositoryImpl(retrofitService, categoryPath), requireActivity().application)).get(BooksViewModel::class.java)
    }

    private fun observeViewModel() {
        observeResponse()
        observeCurrentBook()
        observeCovers()
    }



    // BUTTON CONTROLS

    private fun assignControls() {

        binding.ivGetBack.setOnClickListener { requireActivity().onBackPressed() }

        binding.buttonBuyAtAmazon.setOnClickListener {
            viewModel.getCurrentBook().value?.amazonProductUrl?.let { amazonUrl -> openExternalBrowser(amazonUrl) }
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
//                .setDuration(Duration.Nor127mal.duration)
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

    fun openExternalBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }


    // CARDS BEHAVIOR

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
    }

    override fun onCardDisappeared(view: View?, position: Int) {

        if (position < numberOfBooks - 1) {
            setBackgroundImage(viewModel.getCovers().value?.get(position+1))
        }
        else {
            enableNoMoreCardsMode()
        }
    }

    private fun initializeCards() {
        manager.apply {
            setStackFrom(StackFrom.Left)
            setTranslationInterval(CARD_TRANSLATION_INTERVAL)
            setVisibleCount(COUNT_OF_VISIBLE_CARDS)
            setScaleInterval(NEXT_CARD_INITIAL_SCALE)
            setSwipeThreshold(CARD_SWIPE_THRESHOLD)
            setMaxDegree(CARD_SWIPE_ANGLE)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(false)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        binding.apply {
            card.layoutManager = manager
            card.adapter = adapter
        }
    }

    private fun enableNoMoreCardsMode() {

        animateBookInformationViewsOut()
        animateExitInformationViewsIn()

        Handler(Looper.getMainLooper()).postDelayed( {
            leaveScreen()
        }, ANIMATION_DURATION_SHORT)

    }

    private fun animateBookInformationViewsOut() {

        YoYo
            .with(Techniques.FadeOut)
            .duration(ANIMATION_DURATION_SHORT)
            .apply {
                playOn(binding.layoutBookAchievement)
                playOn(binding.divider)
            }

        YoYo
            .with(Techniques.SlideOutLeft)
            .duration(ANIMATION_DURATION_SHORT)
            .apply {
                playOn(binding.tvBookTitle)
                playOn(binding.tvBookAuthor)
                playOn(binding.tvSynopsisTitle)
                playOn(binding.tvBookDescription)
            }

        YoYo
            .with(Techniques.SlideOutRight)
            .duration(ANIMATION_DURATION_SHORT)
            .apply {
                playOn(binding.tvBookRank)
                playOn(binding.ivRankDynamics)
            }

        YoYo
            .with(Techniques.SlideOutDown)
            .duration(ANIMATION_DURATION_SHORT)
            .apply {
                playOn(binding.buttonBuyAtAmazon)
                playOn(binding.layoutBookDescription)
                playOn(binding.viewBookShelf)
            }
    }

    private fun animateExitInformationViewsIn() {

        binding.layoutNothingToShow.root.visibility = View.VISIBLE

        YoYo
            .with(Techniques.SlideInDown)
            .duration(ANIMATION_DURATION_LONG)
            .playOn(binding.layoutNothingToShow.root)

    }

    private fun leaveScreen() {
    }


    // OBSERVING

    private fun observeResponse() {
        viewModel.getApiResponse().observe(
            viewLifecycleOwner,
            {
                adapter.setListOfBooks(it)
                binding.publication = it.results
                numberOfBooks = it.numResults
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
                it?.let {updateRankIcon(it.rank, it.rankLastWeek)}
                binding.tvBookTitle.isSelected = true
            }
        )
    }

    private fun updateRankIcon(rank: Int?, rankLastWeek: Int?) {

        rank ?: return
        rankLastWeek ?: return

        // -1 - negative, 0 - neutral, 1 - positive
        var rankDynamics = 0

        when {
            (rank == rankLastWeek && rankLastWeek != 0) -> rankDynamics = 0
            (rank < rankLastWeek || rankLastWeek == 0) -> rankDynamics = 1
            (rank > rankLastWeek && rankLastWeek != 0) -> rankDynamics = -1
        }

        when {
            rankDynamics != previousBookRankDynamics && rankDynamics == 1 -> {
                binding.ivRankDynamics.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_caret_up, resources.newTheme()))
                playRankIconAnimation()
            }

            rankDynamics != previousBookRankDynamics && rankDynamics == -1 -> {
                binding.ivRankDynamics.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_caret_down, resources.newTheme()))
                playRankIconAnimation()
            }

            rankDynamics != previousBookRankDynamics && rankDynamics == 0 ->
                binding.ivRankDynamics.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_empty, resources.newTheme()))
        }

        previousBookRankDynamics = rankDynamics
    }

    private fun playRankIconAnimation() {
        YoYo
            .with(Techniques.FlipInX)
            .duration(ANIMATION_DURATION_SHORT)
            .playOn(binding.ivRankDynamics)
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

        binding.layoutBackground.background = desaturateDrawable(drawable)

        Blurry.delete(binding.layoutBackground)

        Blurry.with(context)
            .radius(BLUR_LEVEL)
            .sampling(SAMPLING_LEVEL)
            .color(Color.argb(191, 0, 0, 0))
            .onto(binding.layoutBackground)
        YoYo.with(Techniques.FadeIn).duration(ANIMATION_DURATION_SHORT).playOn(binding.layoutBackground)
    }

    private fun desaturateDrawable(drawable: Drawable?): Drawable? {
        val desaturatedDrawable = drawable?.constantState?.newDrawable()?.mutate()
        val matrix = ColorMatrix()
        matrix.setSaturation(BACKGROUND_SATURATION)
        desaturatedDrawable?.colorFilter = ColorMatrixColorFilter(matrix)

        return desaturatedDrawable
    }
}