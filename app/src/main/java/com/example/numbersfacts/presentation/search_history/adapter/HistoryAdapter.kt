package com.example.numbersfacts.presentation.search_history.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.numbersfacts.databinding.HistoryItemBinding
import com.example.numbersfacts.domain.models.NumberFact
import com.example.numbersfacts.presentation.search_history.SearchHistoryFragmentDirections
import com.example.numbersfacts.presentation.search_history.SearchHistoryViewModel

class HistoryAdapter(
    var history: List<NumberFact>,
) : RecyclerView.Adapter<HistoryAdapter.PlacesViewHolder>() {
    inner class PlacesViewHolder(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(layoutInflater, parent, false)
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.binding.apply {
            number.text = history[position].number.toString()
            fact.text = HtmlCompat.fromHtml(history[position].fact.drop(history[position].number.toString().length + 1), HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.itemView.setOnClickListener{
                val action =
                    SearchHistoryFragmentDirections.actionSearchHistoryFragmentToDetailFragment(
                        history[position]
                    )
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = history.size


}