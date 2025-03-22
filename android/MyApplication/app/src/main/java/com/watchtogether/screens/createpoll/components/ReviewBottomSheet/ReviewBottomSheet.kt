package com.watchtogether.screens.createpoll.components.ReviewBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.components.ReviewBottomSheet.components.CreatePollButton
import com.watchtogether.screens.createpoll.components.ReviewBottomSheet.components.ReviewHeader
import com.watchtogether.screens.createpoll.components.ReviewBottomSheet.components.SelectedMoviesRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewBottomSheet(
    selectedMovies: List<Movie>,
    onRemoveMovie: (Movie) -> Unit,
    onCreatePoll: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Configure the sheet state to expand fully
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header component
                ReviewHeader(
                    selectedCount = selectedMovies.size
                )
                
                // Selected movies row component
                SelectedMoviesRow(
                    selectedMovies = selectedMovies,
                    onRemoveMovie = onRemoveMovie
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Create poll button component
                CreatePollButton(
                    enabled = selectedMovies.isNotEmpty(),
                    onClick = onCreatePoll
                )
            }
        }
    )
} 