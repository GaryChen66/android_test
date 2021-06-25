package co.metalab.tech.interview.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
    @GET("/pokemon")
    suspend fun getAllPokemon(): List<Pokemon>

    @GET("/pokemon/{id}/evolutions")
    suspend fun getEvolutions(@Path("id") id: Int): List<Evolution>
}
