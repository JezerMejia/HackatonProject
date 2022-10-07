package com.jezerm.hackatonproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jezerm.hackatonproject.databinding.FragmentHowToBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HowToFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HowToFragment : Fragment() {

    private lateinit var binding: FragmentHowToBinding
    private val list = ArrayList<Int>()
    private var listIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        this.list.add(R.drawable.como_jugar_1)
        this.list.add(R.drawable.como_jugar_2)
        this.list.add(R.drawable.como_jugar_3)
    }

    public fun getImageResource(): Int {
        if (this.listIndex >= this.list.size - 1)
            listIndex = 0
        else
            listIndex++
        return this.list[this.listIndex]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = FragmentHowToBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding.imgView.setImageResource(this.list[0])
        this.binding.imgView.setOnClickListener {
            this.binding.imgView.setImageResource(this.getImageResource())
        }
    }
}