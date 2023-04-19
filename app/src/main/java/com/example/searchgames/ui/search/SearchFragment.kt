package com.example.searchgames.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchgames.MainActivity
import com.example.searchgames.R
import com.example.searchgames.databinding.FragmentSearchBinding
import com.example.searchgames.ui.viewmodel.SearchGamesViewModel
import com.example.searchgames.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds


@AndroidEntryPoint
class SearchFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val viewModel: SearchGamesViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: SearchResultAdapter

    private lateinit var textChangeCountDownJob: Job


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.search_fragment_label)

        setupRecyclerView()
        setUpSearchBar()
        setupObserver()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.searchGame()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = SearchResultAdapter(findNavController())
        binding.searchResultsRecyclerview.adapter = adapter
        binding.searchResultsRecyclerview.layoutManager = GridLayoutManager(context, 2)

        val onScrollListener = object :
            EndlessRecyclerViewScrollListener(binding.searchResultsRecyclerview.layoutManager as GridLayoutManager) {
            override fun onLoadMore() {
                viewModel.loadMoreGames()
            }
        }
        binding.searchResultsRecyclerview.addOnScrollListener(onScrollListener)
    }


    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                SearchFragmentHelper.updateSearchListState(viewModel, binding, context, adapter)
            }
        }
    }

    private fun setUpSearchBar() {
        binding.searchEditText.doAfterTextChanged { query ->
            if (::textChangeCountDownJob.isInitialized)
                textChangeCountDownJob.cancel()
            textChangeCountDownJob = lifecycleScope.launch {
                delay(500.milliseconds)
                query?.let {
                    viewModel.searchGame(it.toString())
                }
            }
        }
    }

}