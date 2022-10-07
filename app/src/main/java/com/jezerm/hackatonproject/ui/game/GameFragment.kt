package com.jezerm.hackatonproject.ui.game

import android.os.Bundle
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.jezerm.hackatonproject.R
import com.jezerm.hackatonproject.databinding.FragmentGameBinding
import com.yuyakaido.android.cardstackview.*

val situationsBank = ArrayList<GameSituation>()
var currentSituation = 0

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment(), CardStackListener {
    private lateinit var binding: FragmentGameBinding
    private val manager by lazy { CardStackLayoutManager(this.context, this) }
    private val adapter by lazy { CardAdapter(getItems()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        this.init()
    }

    private fun init() {
        situationsBank.add(
            GameSituation(
                "Estás en el colegio y un hombre te queda viendo fijamente. ¿Qué haces?",
                "Preguntarle qué está haciendo",
                "Correr hacia un profesor o profesora y decirle",
                CorrectSide.RIGHT
            )
        )
        situationsBank.add(
            GameSituation(
                "Ves que una persona adulta besa o toca a una niña de forma morbosa. ¿Los ignoras?",
                "No",
                "Sí",
                CorrectSide.LEFT
            )
        )
        situationsBank.add(
            GameSituation(
                "Acabas de salir del colegio. Alguien desconocido te ofrece dinero a cambio de acompañarlo a su hogar.",
                "Aceptas",
                "Le dices que no debes hablar con desconocidos",
                CorrectSide.RIGHT
            )
        )
    }
    private fun getItems(): ArrayList<GameSituation> {
//        arrayList.add(situationsBank[currentSituation])
        return situationsBank
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

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        val card = this.manager.topView
        val cardImg = card.findViewById<ImageFilterView>(R.id.imgView)
        val cardText = card.findViewById<TextView>(R.id.tvMessage)
        val situation = situationsBank[this.manager.topPosition]

        if (ratio > 0.1) {
            cardImg.brightness = 0.4f
            cardText.text =
                if (direction == Direction.Right) situation.rightMessage else situation.leftMessage
        } else {
            cardImg.brightness = 1.0f
            cardText.setText("")
        }
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
        val card = this.manager.topView
        val cardImg = card.findViewById<ImageFilterView>(R.id.imgView)
        val cardText = card.findViewById<TextView>(R.id.tvMessage)
        cardImg.brightness = 1.0f
        cardText.setText("")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        val situation = situationsBank[position]
        this.binding.tvTitle.text = situation.situation
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        println("Bank: ${situationsBank}")
//        situationsBank.removeFirst()
//        this.binding.cardStack.adapter?.notifyItemRemoved(0)
//        if (currentSituation >= situationsBank.size) {
//            currentSituation = 0
//        } else
//            currentSituation++
//        this.arrayList.add(situationsBank[currentSituation])
//        this.binding.cardStack.adapter?.notifyItemRangeInserted(0, 1)
    }
}