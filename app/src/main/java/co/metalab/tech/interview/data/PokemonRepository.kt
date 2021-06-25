package co.metalab.tech.interview.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object PokemonRepository {

    private val pokemonCache: HashMap<Int, Pokemon> = hashMapOf()

    suspend fun getPokemonList(): Result<List<Pokemon>> = try {
        val pokemon = createPokeService()
            .getAllPokemon()
            .also { pokemonList -> pokemonList.forEach { pokemon -> pokemonCache[pokemon.id] = pokemon } }

        Result.Success(pokemon)
    } catch (exception: Exception) {
        Result.Failure(exception)
    }

    suspend fun getEvolutions(pokeId: Int): Result<List<Evolution>> = try {
        Result.Success(createPokeService().getEvolutions(pokeId))
    } catch (exception: Exception) {
        Result.Failure(exception)
    }

    private fun createPokeService() = Retrofit.Builder()
        .baseUrl("https://hiring-test-api.herokuapp.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(PokeService::class.java)
}
