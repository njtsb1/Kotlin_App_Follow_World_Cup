package me.dio.cup.catar.remote.source

import me.dio.cup.catar.data.source.MatchesDataSource
import me.dio.cup.catar.domain.model.MatchDomain
import me.dio.cup.catar.remote.extensions.getOrThrowDomainError
import me.dio.cup.catar.remote.mapper.toDomain
import me.dio.cup.catar.remote.services.MatchesServices
import javax.inject.Inject

class MatchDataSourceRemote @Inject constructor(
    private val service: MatchesServices
) : MatchesDataSource.Remote {

    override suspend fun getMatches(): List<MatchDomain> {
        return runCatching {
            service.getMatches()
        }.getOrThrowDomainError().toDomain()
    }
}
