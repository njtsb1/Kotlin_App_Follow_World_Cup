package me.dio.cup.catar.domain.usecase

import me.dio.cup.catar.domain.model.Match
import me.dio.cup.catar.domain.repositories.MatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    operator fun invoke(): Flow<List<Match>> = repository.getMatches()
}
