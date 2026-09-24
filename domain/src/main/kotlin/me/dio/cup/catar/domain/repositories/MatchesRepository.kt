package me.dio.cup.catar.domain.repositories

import kotlinx.coroutines.flow.Flow
import me.dio.cup.catar.domain.model.Match

interface MatchesRepository {
    fun getMatches(): Flow<List<Match>>
}
