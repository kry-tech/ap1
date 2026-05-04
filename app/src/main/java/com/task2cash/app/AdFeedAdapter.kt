package com.task2cash.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdFeedAdapter(
    private val anuncios: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<AdFeedAdapter.ViewHolder>() {

    private val assistidos = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val texto = if (assistidos.contains(position)) {
            "Anúncio ${anuncios[position]} - ✓ ASSISTIDO"
        } else {
            "Anúncio ${anuncios[position]} - Clique para assistir"
        }
        holder.textView.text = texto

        holder.itemView.setOnClickListener {
            if (!assistidos.contains(position)) {
                onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int = anuncios.size

    fun marcarAssistido(posicao: Int) {
        assistidos.add(posicao)
        notifyItemChanged(posicao)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }
}
