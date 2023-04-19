package com.example.searchgames.ui.gamedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.searchgames.MainActivity
import com.example.searchgames.databinding.FragmentGameDetailsBinding
import com.example.searchgames.ui.viewmodel.SearchGamesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.coil.CoilImagesPlugin
import javax.inject.Inject


@AndroidEntryPoint
class GameDetailsFragment @Inject constructor() : DialogFragment() {

    private var _binding: FragmentGameDetailsBinding? = null

    private val args: GameDetailsFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: SearchGamesViewModel by activityViewModels()

    private var markwon: Markwon? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)

        markwon = context?.let {
            Markwon.builder(it)
                .usePlugin(CoilImagesPlugin.create(it))
                .usePlugin(HtmlPlugin.create())
                .build()
        }



        viewModel.searchResult.value.find { it.id == args.gameId }?.let {
            binding.gameDetailsImage.load(it.largeImageUrl){
                crossfade(true)
            }
            binding.gameDetailsName.text = it.name
            (activity as MainActivity).supportActionBar?.title = it.name
                binding.gameDetailsReleaseShortDescription.text = it.deck
            it.description?.let { it1 -> markwon?.setMarkdown(binding.gameDetailsDescription, it1) }

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}