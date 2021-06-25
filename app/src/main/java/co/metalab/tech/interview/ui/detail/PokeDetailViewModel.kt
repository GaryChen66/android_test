package co.metalab.tech.interview.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.metalab.tech.interview.R
import co.metalab.tech.interview.common.prettyPrintId
import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.data.Result
import co.metalab.tech.interview.domain.GetEvolutions
import co.metalab.tech.interview.domain.GetPokemon
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class PokeDetailViewModel : ViewModel() {

    private val getPokemon = GetPokemon()
    private val getEvolutions = GetEvolutions()
    private lateinit var context: Context

    private val pokeDetails = MutableLiveData<PokeDetails>()
    fun pokeDetails(): LiveData<PokeDetails> = pokeDetails

    private val evolutionItems = MutableLiveData<List<EvolutionItem>>()
    fun evolutionItems(): LiveData<List<EvolutionItem>> = evolutionItems

    private val loading = MutableLiveData<Boolean>()
    fun loading(): LiveData<Boolean> = loading

    private val error = MutableLiveData<Boolean>()
    fun error(): LiveData<Boolean> = error

    private val showNoEvolutions = MutableLiveData<Boolean>()
    fun showNoEvolutions(): LiveData<Boolean> = showNoEvolutions

    private val goToPokeDetail = MutableLiveData<Int>()
    fun goToPokeDetail(): LiveData<Int> = goToPokeDetail

    fun start(pokeId: Int, context: Context) = viewModelScope.launch {
        this@PokeDetailViewModel.context = context
        if (pokeDetails.value == null) {
            loading.value = true

            pokeDetails.value = getPokemon(pokeId).toPokeDetails()
            evolutionItems.value = getEvolutionItems(pokeId)

            loading.value = false
        }
    }

    fun pokemonClicked(pokeId: Int) {
        goToPokeDetail.value = pokeId
    }

    private suspend fun getPokemon(pokeId: Int): Pokemon? {
        return when (val result = getPokemon.execute(pokeId)) {
            is Result.Success -> result.data
            is Result.Failure -> null
        }
    }

    @SuppressLint("DefaultLocale")
    private suspend fun getEvolutionItems(pokeId: Int): List<EvolutionItem> {
        error.value = false
        showNoEvolutions.value = false
        return when (val result = getEvolutions.execute(pokeId)) {
            is Result.Success -> {
                showNoEvolutions.value = result.data.isEmpty()
                result.data.mapNotNull { evolution ->
                    val evolutionPokeId = when {
                        evolution.evolves_from != null -> evolution.evolves_from
                        evolution.evolves_into != null -> evolution.evolves_into
                        else -> return@mapNotNull null
                    }

                    val evolutionRelation = when {
                        evolution.evolves_from != null -> EvolutionRelation.FROM
                        evolution.evolves_into != null -> EvolutionRelation.INTO
                        else -> return@mapNotNull null
                    }

                    val evolutionPokemon = getPokemon(evolutionPokeId) ?: return@mapNotNull null

                    EvolutionItem(
                        evolutionPokemon.id,
                        evolutionPokemon.identifier.capitalize(),
                        evolutionPokemon.image_url,
                        evolutionRelation,
                        evolution.trigger
                    )
                }
            }
            is Result.Failure -> {
                error.value = true
                emptyList()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun Pokemon?.toPokeDetails() = PokeDetails(
        this?.image_url,
        this?.id?.prettyPrintId(),
        this?.identifier?.capitalize(),
        this?.types ?: emptyList(),
        context.getString(R.string.STRING_does_not_evolve, this?.identifier?.capitalize())
    )
}
