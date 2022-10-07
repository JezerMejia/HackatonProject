package com.jezerm.hackatonproject.ui.game


import android.animation.ArgbEvaluator
import android.animation.FloatEvaluator
import android.animation.ValueAnimator
import android.graphics.Color.red
import android.os.Bundle
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.jezerm.hackatonproject.R
import com.jezerm.hackatonproject.databinding.FragmentGameBinding
import com.yuyakaido.android.cardstackview.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment(), CardStackListener {
    private lateinit var binding: FragmentGameBinding
    private val manager by lazy { CardStackLayoutManager(this.context, this) }
    private val adapter by lazy { CardAdapter(getItems()) }
    private lateinit var situationList: ArrayList<GameSituation>

    companion object {
        const val SITUATION_LIST = "situation_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val array = it.getParcelableArray(SITUATION_LIST) as Array<GameSituation>
            this.situationList = array.toCollection(ArrayList())
        }
    }

    private fun getItems(): ArrayList<GameSituation> {
//        arrayList.add(situationsBank[currentSituation])
        return this.situationList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentGameBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView = this.binding.cardStack

        this.manager.setStackFrom(StackFrom.None)
        this.manager.setVisibleCount(3)
        this.manager.setTranslationInterval(8.0f)
        this.manager.setScaleInterval(0.95f)
        this.manager.setSwipeThreshold(0.3f)
        this.manager.setMaxDegree(-30.0f)
        this.manager.setDirections(Direction.FREEDOM)
        this.manager.setCanScrollHorizontal(true)
        this.manager.setCanScrollVertical(false)
        this.manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        this.manager.setOverlayInterpolator(LinearInterpolator())

        cardView.layoutManager = this.manager
        cardView.adapter = adapter
        cardView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private var imgAnimator: ValueAnimator? = null
    private var textAnimator: ValueAnimator? = null
    private lateinit var imgAnimatorListener: FadeCardImgListener
    private lateinit var textAnimatorListener: FadeCardTextListener

    inner class FadeCardImgListener(val cardImg: ImageFilterView) :
        ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationUpdate(p0: ValueAnimator) {
            cardImg.brightness = p0.animatedValue as Float
        }
    }

    inner class FadeCardTextListener(val cardText: TextView) :
        ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationUpdate(p0: ValueAnimator) {
            cardText.alpha = p0.animatedValue as Float
        }
    }

    private fun fadeCardImgBrightness(darken: Boolean) {
        val card = this.manager.topView
        val cardImg = card.findViewById<ImageFilterView>(R.id.imgView)
        val cardText = card.findViewById<TextView>(R.id.tvMessage)
        this.imgAnimatorListener = FadeCardImgListener(cardImg)
        this.textAnimatorListener = FadeCardTextListener(cardText)

        this.imgAnimator?.cancel()
        this.imgAnimator?.removeAllListeners()
        this.textAnimator?.cancel()
        this.textAnimator?.removeAllListeners()

        if (darken) {
            this.imgAnimator = ValueAnimator.ofObject(FloatEvaluator(), cardImg.brightness, 0.4f)
            this.textAnimator = ValueAnimator.ofObject(FloatEvaluator(), cardText.alpha, 1.0f)
        } else {
            this.imgAnimator = ValueAnimator.ofObject(FloatEvaluator(), cardImg.brightness, 1.0f)
            this.textAnimator = ValueAnimator.ofObject(FloatEvaluator(), cardText.alpha, 0.0f)
        }
        this.imgAnimator?.duration = 50
        this.textAnimator?.duration = 50

        this.imgAnimator?.addUpdateListener(this.imgAnimatorListener)
        this.textAnimator?.addUpdateListener(this.textAnimatorListener)

        this.imgAnimator?.start()
        this.textAnimator?.start()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        val card = this.manager.topView
        val cardText = card.findViewById<TextView>(R.id.tvMessage)
        val situation = this.situationList[this.manager.topPosition]

        if (ratio > 0.1) {
            cardText.text =
                if (direction == Direction.Right) situation.rightMessage else situation.leftMessage
            this.fadeCardImgBrightness(true)
        } else {
            this.fadeCardImgBrightness(false)
        }
    }

    override fun onCardSwiped(direction: Direction?) {
        if (this.manager.topPosition >= this.situationList.size) return

        val situation = this.situationList[this.manager.topPosition - 1]

        if (direction == Direction.Left && situation.correctAnswer == CorrectSide.LEFT) return
        if (direction == Direction.Right && situation.correctAnswer == CorrectSide.RIGHT) return

        val container = this.binding.containerGame
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), resources.getColor(R.color.secondary), resources.getColor(R.color.red))
        animator.duration = 250
        animator.addUpdateListener {
            container.setBackgroundColor(it.animatedValue as Int)
        }
        animator.start()

        val animator2 = ValueAnimator.ofObject(ArgbEvaluator(), resources.getColor(R.color.red), resources.getColor(R.color.secondary))
        animator2.startDelay = 1000
        animator2.duration = 250
        animator2.addUpdateListener {
            container.setBackgroundColor(it.animatedValue as Int)
        }
        animator2.start()

//        this.binding.containerGame.setBackgroundColor(resources.getColor(R.color.black))
        Toast.makeText(this.context, "Incorrecto. Esa acción está mal.", Toast.LENGTH_SHORT).show()
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
        this.fadeCardImgBrightness(false)
    }

    override fun onCardAppeared(view: View?, position: Int) {
        val situation = this.situationList[position]
        this.binding.tvTitle.text = situation.situation
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        println("Bank: ${this.situationList}")
    }
}