package co.metalab.tech.interview.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.metalab.tech.interview.R
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_detail.*

class PokeDetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<PokeDetailViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PokeDetailFragmentArgs by navArgs()

        toolbar.setupWithNavController(findNavController())

        val adapter = PokeEvolutionAdapter { pokeId -> viewModel.pokemonClicked(pokeId) }

        pokeList.layoutManager = LinearLayoutManager(context)
        pokeList.adapter = adapter

        viewModel.pokeDetails().observe(viewLifecycleOwner, Observer { details -> bindPokeDetails(details) })

        viewModel.evolutionItems().observe(viewLifecycleOwner, Observer { items ->
            adapter.items = items

        })

        viewModel.loading().observe(viewLifecycleOwner, Observer { loading ->
            progressBar.isVisible = loading
            evolutions.isVisible = !loading
        })

        viewModel.error().observe(viewLifecycleOwner, Observer { error ->
            errorEvolutions.isVisible = error
        })

        viewModel.showNoEvolutions().observe(viewLifecycleOwner, Observer { showNoEvolutions ->
            noEvolutions.isVisible = showNoEvolutions
        })

        viewModel.goToPokeDetail().observe(viewLifecycleOwner, Observer { pokeId ->
            goToPokeDetail(pokeId)
        })

        viewModel.start(args.pokeId)
    }

    private fun bindPokeDetails(details: PokeDetails) {
        Glide.with(this).load(details.imageUrl).into(pokePhoto)
        pokeNumber.text = details.formattedId
        pokeName.text = details.name

        pokeTypesGroup.removeAllViews()
        details.types.sortedBy { it.identifier }.forEach { type ->
            val chip = Chip(context).apply {
                text = type.identifier.capitalize()
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setChipBackgroundColorResource(type.getColor())
            }

            pokeTypesGroup.addView(chip)
        }

        noEvolutions.text = details.noEvolutionsText
    }

    private fun goToPokeDetail(pokeId: Int) {
        findNavController().navigate(PokeDetailFragmentDirections.actionPokeDetailFragmentToPokeDetailFragment(pokeId))
    }
}
