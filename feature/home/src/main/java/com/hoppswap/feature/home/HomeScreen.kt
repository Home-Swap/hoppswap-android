package com.hoppswap.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hoppswap.core.common.util.toInitials
import com.hoppswap.core.designsystem.component.EmptyLabel
import com.hoppswap.core.designsystem.component.HeadingSmall
import com.hoppswap.core.designsystem.theme.Medium
import com.hoppswap.core.designsystem.theme.Small
import com.hoppswap.data.auth.model.Chat
import com.hoppswap.data.auth.model.Property
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Composable
fun HomeScreen(
    matchClicked: (Property) -> Unit,
    chatClicked: (Chat) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                is HomeAction.NavigateToPropertyDetails -> matchClicked(it.property)
                is HomeAction.NavigateToChatPage -> chatClicked(it.chat)
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.onIntent(HomeIntent.OnLoadMatches)
    }

    Column(modifier = Modifier.padding(Medium)) {
        HeadingSmall(text = R.string.heading_matches, modifier = Modifier.padding(vertical = Medium))
        if (uiState.matches.isNotEmpty()) {
            MatchesList(uiState.matches) { viewModel.onIntent(HomeIntent.OnMatchClicked(it)) }
        } else {
            EmptyMatchView()
        }
        Spacer(modifier = Modifier.height(Small))
        HeadingSmall(text = R.string.heading_messages, modifier = Modifier.padding(vertical = Medium))
        if (uiState.chats.isNotEmpty()) {
            ChatsList(uiState.chats) { viewModel.onIntent(HomeIntent.OnChatClicked(it)) }
        } else {
            EmptyChatView()
        }
    }
}

@Composable
private fun MatchesList(matches: List<Property>, onMatchClicked: (Property) -> Unit) {
    LazyRow(horizontalArrangement = spacedBy(Medium)) {
        items(matches) {
            MatchItem(it, onMatchClicked)
        }
    }
}

@Composable
private fun ColumnScope.ChatsList(chats: List<Chat>, onChatClicked: (Chat) -> Unit) {
    LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = spacedBy(Medium)) {
        items(chats) {
            ChatItem(it, onChatClicked)
        }
    }
}

@Composable
private fun EmptyMatchView() {
    Box {
        EmptyLabel(text = R.string.label_no_matches)
    }
}

@Composable
private fun ColumnScope.EmptyChatView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        EmptyLabel(text = R.string.label_no_matches)
    }
}

@Composable
private fun MatchItem(match: Property, onMatchClicked: (Property) -> Unit) {
    Column(
        modifier = Modifier.clickable { onMatchClicked(match) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = match.photos.first(),
            contentDescription = stringResource(R.string.cont_desc_property_photo),
            modifier = Modifier
                .size(size = 100.dp)
                .clip(shape = RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Small))
        Text(
            text = match.owner.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun ChatItem(chat: Chat, onChatClicked: (Chat) -> Unit) {
    Row(
        modifier = Modifier.clickable { onChatClicked(chat) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (chat.profilePhoto.isNotEmpty()) {
            AsyncImage(
                model = chat.profilePhoto,
                contentDescription = stringResource(R.string.cont_desc_property_photo),
                modifier = Modifier
                    .size(size = 80.dp)
                    .clip(shape = RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chat.name.toInitials(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
        Spacer(modifier = Modifier.width(Medium))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = spacedBy(Small)
        ) {
            Text(
                text = chat.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = chat.text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}
