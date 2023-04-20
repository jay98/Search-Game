package com.example.searchgames.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchgames.MainActivity
import com.example.searchgames.R
import com.example.searchgames.databinding.FragmentSearchBinding
import com.example.searchgames.ui.viewmodel.SearchGamesViewModel
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = SearchResultAdapter(findNavController())
        binding.searchResultsRecyclerview.adapter = adapter
        binding.searchResultsRecyclerview.layoutManager = GridLayoutManager(context, 2)

        val onScrollListener = object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreGames()
                }
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

        // hide keyboard on search press
        binding.searchEditText.setOnEditorActionListener { _, _, _ ->
            try {
                val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                true
            }
        }

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