package com.hoppswap.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hoppswap.core.designsystem.component.EmptyLabel
import com.hoppswap.core.designsystem.theme.Medium
import com.hoppswap.core.designsystem.theme.Shapes
import com.hoppswap.core.designsystem.theme.Small
import com.hoppswap.data.auth.model.Property
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Composable
fun HomeScreen(propertyClicked: (Property) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                is HomeAction.NavigateToPropertyDetails -> propertyClicked(it.property)
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.onIntent(HomeIntent.OnLoadMatches)
    }

    Box {
        if (uiState.matches.isNotEmpty()) {
            MatchesList(uiState.matches) { viewModel.onIntent(HomeIntent.OnMatchClicked(it)) }
        } else {
            EmptyMatchView()
        }
    }
}

@Composable
private fun MatchesList(matches: List<Property>, onMatchClicked: (Property) -> Unit) {
    LazyColumn {
        items(matches) {
            MatchRow(it, onMatchClicked)
        }
    }
}

@Composable
private fun EmptyMatchView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        EmptyLabel(text = R.string.label_no_matches)
    }
}

@Composable
private fun MatchRow(match: Property, onMatchClicked: (Property) -> Unit) {
    Row(
        modifier = Modifier
            .padding(Medium)
            .clickable { onMatchClicked(match) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = match.photos.first(),
            contentDescription = stringResource(R.string.cont_desc_property_photo),
            modifier = Modifier
                .width(100.dp)
                .clip(shape = Shapes.small),
            contentScale = ContentScale.Crop
        )
        MatchDetailsRow(match)
    }
}

@Composable
private fun MatchDetailsRow(match: Property) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Medium), verticalArrangement = spacedBy(Small)
    ) {
        Text(
            text = match.owner.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = match.title,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = match.city,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
