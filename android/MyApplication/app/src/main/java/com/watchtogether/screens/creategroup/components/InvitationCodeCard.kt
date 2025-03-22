package com.watchtogether.screens.creategroup.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.watchtogether.screens.creategroup.modifiers.CreateGroupModifiers

@Composable
fun InvitationCodeCard(
    invitationCode: String,
    groupName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier.then(CreateGroupModifiers.invitationCard())
    ) {
        Column(
            modifier = CreateGroupModifiers.invitationCardContent(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = invitationCode,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = CreateGroupModifiers.invitationCodeText()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Copy button
                IconButton(
                    onClick = { 
                        copyToClipboard(context, invitationCode)
                        Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.FileCopy,
                        contentDescription = "Copy code"
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Share button
                IconButton(
                    onClick = { 
                        shareInvitationCode(context, groupName, invitationCode)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share code"
                    )
                }
            }
        }
    }
}

// Helper function to copy text to clipboard
private fun copyToClipboard(context: Context, text: String) {
    val clipboard = getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("Invitation Code", text)
    clipboard?.setPrimaryClip(clip)
}

// Helper function to share invitation code
private fun shareInvitationCode(context: Context, groupName: String, code: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Join my WatchTogether group")
        putExtra(Intent.EXTRA_TEXT, "Join my WatchTogether group \"$groupName\" with invitation code: $code")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share invitation code"))
} 