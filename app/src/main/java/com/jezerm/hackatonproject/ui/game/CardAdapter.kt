package com.jezerm.hackatonproject.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jezerm.hackatonproject.databinding.ComponentCardBinding

class CardAdapter(
    private var situations: List<GameSituation> = emptyList()
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private lateinit var binding: ComponentCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.binding = ComponentCardBinding.inflate(inflater, parent, false)
        return ViewHolder(this.binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val situation = situations[position]
        holder.load(situation)
    }

    override fun getItemCount(): Int {
        return situations.size
    }

    fun setSituations(spots: List<GameSituation>) {
        this.situations = spots
    }

    fun getSituations(): List<GameSituation> {
        return situations
    }

    class ViewHolder(private val binding: ComponentCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun load(situation: GameSituation) {
            this.binding.tvMessage.setText("")
        }
    }
}