package com.futureus.cameraxapp.presentation.chat

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@OptIn(ExperimentalPermissionsApi::class)
@Destination<RootGraph>
@Composable
fun ChatScreen(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onMessageSend: () -> Unit,
    onMessageBroadcast: () -> Unit
) {

    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (!notificationPermission.status.isGranted) {
                notificationPermission.launchPermissionRequest()
            } else {
                Toast.makeText(context, "Permission Given Already", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Ask for permission")
        }
        Text(
            text = "Permission Status : ${notificationPermission.status.isGranted}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = messageText,
            onValueChange = onMessageChange,
            placeholder = {
                Text("Enter a message")
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = onMessageSend
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
            Spacer(Modifier.width(16.dp))
            IconButton(
                onClick = onMessageBroadcast
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Broadcast"
                )
            }
        }
    }
}