package me.dio.cup.catar.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.dio.cup.catar.domain.models.Match
import me.dio.cup.catar.domain.usecase.DisableNotificationUseCase
import me.dio.cup.catar.domain.usecase.EnableNotificationUseCase
import me.dio.cup.catar.domain.usecase.GetMatchesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = false,
    val matches: List<Match> = emptyList(),
    val error: String? = null,
    val enabledNotifications: Set<String> = emptySet()
)

class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(isLoading = true))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        observeMatches()
    }

    private fun observeMatches() {
        viewModelScope.launch {
            getMatchesUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true, error = null) } }
                .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
                .collect { matches ->
                    _uiState.update { it.copy(isLoading = false, matches = matches) }
                }
        }
    }

    fun toggleNotification(matchId: String, matchInstant: Instant) {
        viewModelScope.launch {
            val enabled = _uiState.value.enabledNotifications.contains(matchId)
            if (enabled) {
                disableNotificationUseCase(matchId)
                _uiState.update { it.copy(enabledNotifications = it.enabledNotifications - matchId) }
            } else {
                enableNotificationUseCase(matchId, matchInstant)
                _uiState.update { it.copy(enabledNotifications = it.enabledNotifications + matchId) }
            }
        }
    }
}
