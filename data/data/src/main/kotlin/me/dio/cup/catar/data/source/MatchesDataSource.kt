package me.dio.cup.catar.data.source

import kotlinx.coroutines.flow.Flow
import me.dio.cup.catar.domain.model.Match

sealed interface MatchesDataSource {
    interface Remote : MatchesDataSource {
        suspend fun getMatches(): List<Match>
    }

    interface Local : MatchesDataSource {
        fun getActiveNotificationIds(): Flow<Set<String>>
        suspend fun enableNotificationFor(id: String)
        suspend fun disableNotificationFor(id: String)
    }
}
