package com.watchtogether.screens.polldetail.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VoteSuccessScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animation states
    var showContent by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    // Trigger content animation after a delay
    LaunchedEffect(Unit) {
        showContent = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success icon with scale animation
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(120.dp)
                .scale(scale)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Success message with fade-in animation
        Text(
            text = "Vote Submitted!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.scale(scale)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Waiting message
        Text(
            text = "Waiting for poll results...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.scale(scale)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Complete button with slide-up animation
        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
        ) {
            Text("Complete")
        }
    }
} 