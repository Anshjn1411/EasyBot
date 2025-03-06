package com.project.easybot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

import kotlinx.coroutines.launch

class ChatViewModel :ViewModel() {
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = Api.Googleapi
    )



    fun sendMessgae(message :String){

        viewModelScope.launch {
            try{
                val chat = generativeModel.startChat(
                    history =
                    messageList.map {
                        content(it.model){text(it.mess)}
                    }.toList()
                )
                messageList.add(MessageModel("user",message))
                messageList.add(MessageModel("model","Typing..."))

                val response = chat.sendMessage(message)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel("model",response.text.toString()))
            }catch (e :Exception){
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel("model","Error ${e}"))

            }



        }

    }

}