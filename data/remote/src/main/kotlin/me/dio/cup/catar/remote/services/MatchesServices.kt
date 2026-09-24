package me.dio.cup.catar.remote.services

import me.dio.cup.catar.remote.model.MatchRemote
import retrofit2.http.GET

interface MatchesServices {
    @GET("api.json")
    suspend fun getMatches(): List<MatchRemote>
}
