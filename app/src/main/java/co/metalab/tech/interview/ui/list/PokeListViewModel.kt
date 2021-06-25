package co.metalab.tech.interview.ui.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.metalab.tech.interview.common.prettyPrintId
import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.data.Result
import co.metalab.tech.interview.domain.GetPokemonList
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokeListViewModel : ViewModel() {

    private val getPokemonList = GetPokemonList()
    private var getPokeItemsJob: Job? = null
    private var lastQuery: String? = null

    private val pokeItems = MutableLiveData<List<PokeItem>>()
    fun pokeItems(): LiveData<List<PokeItem>> = pokeItems

    private val loading = MutableLiveData<Boolean>()
    fun loading(): LiveData<Boolean> = loading

    private val error = MutableLiveData<Boolean>()
    fun error(): LiveData<Boolean> = error

    private val noResults = MutableLiveData<String?>()
    fun noResults(): LiveData<String?> = noResults

    private val goToPokeDetail = LiveEvent<Int>()
    fun goToPokeDetail(): LiveData<Int> = goToPokeDetail

    fun start() {
        getPokeItems(lastQuery)
    }

    fun pokemonClicked(pokeId: Int) {
        goToPokeDetail.value = pokeId
    }

    fun searchFilterChanged(query: String?) {
        getPokeItems(query)
        this.lastQuery = query
    }

    fun retryClicked() {
        getPokeItems(lastQuery)
    }

    private fun getPokeItems(query: String?) {
        getPokeItemsJob?.cancel()
        getPokeItemsJob = viewModelScope.launch {
            error.value = false
            noResults.value = null
            loading.value = true
            pokeItems.value = when (val result = getPokemonList.execute(query)) {
                is Result.Success -> {
                    if (result.data.isEmpty()) noResults.value = query
                    result.data.toPokeItems()
                }
                is Result.Failure -> {
                    error.value = true
                    emptyList()
                }
            }
            loading.value = false
        }
    }

    @SuppressLint("DefaultLocale")
    private fun List<Pokemon>.toPokeItems(): List<PokeItem> {
        return this.map { pokemon ->
            PokeItem(
                pokemon.id,
                pokemon.id.prettyPrintId(),
                pokemon.identifier.capitalize(),
                pokemon.image_url
            )
        }
    }
}
