package com.example.searchgames.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.searchgames.databinding.GameListItemBinding
import com.example.searchgames.ui.gamedetails.GameDetailsFragmentArgs

// Adapter for a recycler view that displays search results
class SearchResultAdapter(
    private val navController: NavController
) :
    ListAdapter<SearchResultViewData, SearchResultAdapter.ViewHolder>(ProductListDiffCallback) {

    companion object ProductListDiffCallback : DiffUtil.ItemCallback<SearchResultViewData>() {
        override fun areItemsTheSame(
            oldItem: SearchResultViewData,
            newItem: SearchResultViewData
        ): Boolean {
            // ideally would use an id but since we don't have that assuming items are same if their name is the same
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchResultViewData,
            newItem: SearchResultViewData
        ): Boolean {
            //since Product is a data class the equals method checks for content being the same
            return oldItem == newItem
        }
    }

    class ViewHolder(
        private val binding: GameListItemBinding,
        private val navController: NavController
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchResult: SearchResultViewData, position: Int) {
            binding.apply {
                gameName.text = searchResult.name
                gameImage.load(searchResult.imageUrl){
                    crossfade(true)
                }
                gameCard.setOnClickListener {
                    navController.navigate(
                        SearchFragmentDirections.actionSearchFragmentToGameDetailFragment()
                            .setGameId(searchResult.id)
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            GameListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            navController
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }
}