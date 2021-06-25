package co.metalab.tech.interview.ui.list

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import co.metalab.tech.interview.R
import kotlinx.android.synthetic.main.fragment_list.*

class PokeListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel by viewModels<PokeListViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokeItemAdapter(requireContext()) { pokeId -> viewModel.pokemonClicked(pokeId) }
        pokeList.layoutManager = GridLayoutManager(context, 3)
        pokeList.adapter = adapter

        setUpMenu(viewModel)

        errorLoadingText.setOnClickListener { viewModel.retryClicked() }

        viewModel.pokeItems().observe(viewLifecycleOwner, Observer { items ->
            adapter.items = items
        })

        viewModel.noResults().observe(viewLifecycleOwner, Observer { text ->
            noResultsText.isVisible = text != null
            noResultsText.text = getString(R.string.no_results_found_for_STRING, text)
        })

        viewModel.loading().observe(viewLifecycleOwner, Observer { loading ->
            progressBar.isVisible = loading
        })

        viewModel.error().observe(viewLifecycleOwner, Observer { error ->
            errorLoadingText.isVisible = error
        })

        viewModel.goToPokeDetail().observe(viewLifecycleOwner, Observer { pokeId ->
            goToPokeDetail(pokeId)
        })

        viewModel.start()
    }

    private fun setUpMenu(viewModel: PokeListViewModel) {
        toolbar.inflateMenu(R.menu.menu_pokelist)

        val searchView = toolbar.menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = "Search by id or name"
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchFilterChanged(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchFilterChanged(query)
                return false
            }
        })

        toolbar.menu.findItem(R.id.action_search).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }
    }

    private fun goToPokeDetail(pokeId: Int) {
        findNavController().navigate(
            PokeListFragmentDirections
                .actionPokeListFragmentToPokeDetailFragment(pokeId)
        )
    }
}
