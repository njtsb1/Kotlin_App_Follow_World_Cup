package me.dio.cup.catar.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.dio.cup.catar.domain.models.Match
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text("World Cup Tracker") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.error}")
                    }
                }
                else -> {
                    MatchesList(
                        matches = state.matches,
                        enabledNotifications = state.enabledNotifications,
                        onToggleNotification = { matchId, matchInstant ->
                            viewModel.toggleNotification(matchId, matchInstant)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchesList(
    matches: List<Match>,
    enabledNotifications: Set<String>,
    onToggleNotification: (String, java.time.Instant) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        matches.forEach { match ->
            MatchCard(
                match = match,
                enabled = enabledNotifications.contains(match.name),
                onToggle = { onToggleNotification(match.name, match.date) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun MatchCard(match: Match, enabled: Boolean, onToggle: () -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
        .withZone(ZoneId.systemDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = match.stadium.imageUrl,
                contentDescription = match.stadium.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = match.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = formatter.format(match.date), style = MaterialTheme.typography.bodySmall)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "${match.team1}  X  ${match.team2}", style = MaterialTheme.typography.bodyLarge)
                    Button(onClick = onToggle) {
                        Text(if (enabled) "Disable" else "Notify")
                    }
                }
            }
        }
    }
}
