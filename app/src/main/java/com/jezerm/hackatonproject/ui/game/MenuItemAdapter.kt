package com.jezerm.hackatonproject.ui.game

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jezerm.hackatonproject.databinding.GameMenuItemBinding

class MenuItemAdapter(private val list: ArrayList<MenuItem>) :
    RecyclerView.Adapter<MenuItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: GameMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //        val button = view.findViewById<Button>(R.id.button_item)
        fun load(menuItem: MenuItem) {
            with(binding) {
                this.tvStory.text = menuItem.name
                this.itemCard.setOnClickListener {
                    openGameFragment(this.root.context, menuItem)
                }
            }
        }

        /**
         * Abre la vista de edición de un Producto con el id seleccionado
         */
        private fun openGameFragment(context: Context, menuItem: MenuItem) {
            val action =
                GameMenuFragmentDirections.actionNavigationGameMenuToNavigationGame(situationList = menuItem.situations.toTypedArray())
            this.binding.root.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            GameMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.load(this.list[position])
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}