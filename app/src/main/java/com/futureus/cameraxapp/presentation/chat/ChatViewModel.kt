package com.futureus.cameraxapp.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futureus.cameraxapp.data.dto.notification.NotificationBody
import com.futureus.cameraxapp.data.dto.notification.SendMessageDto
import com.futureus.cameraxapp.data.remote.FcmApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val fcmApi: FcmApi
) : ViewModel() {

    var state by mutableStateOf(ChatState())
        private set

    fun onRemoteTokenChange(newToken: String) {
        state = state.copy(remoteToken = newToken)
    }

    fun onSubmitRemoteToken() {
        state = state.copy(
            isEnteringToken = false
        )
    }

    fun onMessageChange(newMessage: String) {
        state = state.copy(messageText = newMessage)
    }

    fun sendMessage(isBroadcast: Boolean){
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = if (isBroadcast) null else state.remoteToken,
                notification = NotificationBody(
                    title = "New message",
                    body = state.messageText
                )
            )

           try {
               if (isBroadcast){
                   fcmApi.broadcast(messageDto)
               } else {
                   fcmApi.sendMessage(messageDto)
               }

               state = state.copy(
                   messageText = "",
               )
           }catch (e: Exception){
               e.printStackTrace()
           }
        }
    }

}