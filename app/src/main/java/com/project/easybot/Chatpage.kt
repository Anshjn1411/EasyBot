package com.project.easybot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Chatpage(modifier: Modifier = Modifier , viewModel: ChatViewModel) {
    Column(
        modifier = modifier
    ) {
         AppHeader()
        MessageArea(modifier = Modifier.weight(1f), messagesList = viewModel.messageList)
        MessageBox(onMessage = {
            viewModel.sendMessgae(it)
        })


    }
}

@Composable
fun MessageArea(modifier: Modifier = Modifier , messagesList : List<MessageModel>){
    if(messagesList.isNotEmpty()){
        LazyColumn(
            modifier = modifier,
            reverseLayout = true,

            ) {
            items(messagesList.reversed()){messages->
                val color : Color
                Spacer(modifier = Modifier.height(10.dp))
                if (messages.model == "user"){
                    color = colorResource(R.color.lightblue)

                }else{
                    color = colorResource(R.color.teal_700)
                }
                MessageRow(message = messages , color = color)

            }

        }
    }else{
        Column(
            modifier = modifier.padding(start = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(painter = painterResource(R.drawable.baseline_question_answer_24), contentDescription = null , modifier= Modifier.size(130.dp))
            Text("Ask any Question" , fontSize = 30.sp)

        }
    }

}

 @Composable
 fun MessageRow(message : MessageModel , color : Color ){

     Row(
         modifier = Modifier.fillMaxWidth()
             .padding(5.dp),
         horizontalArrangement = if (message.model == "user")
             Arrangement.End
         else
             Arrangement.Start

     ) {
          Card(modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically).padding(20.dp),
              shape = RoundedCornerShape(30.dp),
              colors = CardDefaults.cardColors(color),
               elevation = CardDefaults.elevatedCardElevation(20.dp)

          ) {
              SelectionContainer {
                  Text(message.mess, modifier = Modifier.padding(10.dp), color = Color.Black, fontSize = 15.sp)

              }
          }
     }
 }


 @Composable
 fun MessageBox( onMessage : (String) -> Unit ){
      var inputMessage by remember { mutableStateOf("") }
     Row(
         verticalAlignment = Alignment.CenterVertically
     ) {

         OutlinedTextField(
             value = inputMessage,
             onValueChange = {inputMessage = it },
             modifier = Modifier
                 .fillMaxWidth(0.85f)
                 .padding(10.dp),
             shape = RoundedCornerShape(50),
              placeholder = {
                  Text("Type your Message...." , fontStyle = FontStyle.Italic)
              }
         )
          Spacer(Modifier.width(10.dp))
          IconButton(
              onClick = { if(inputMessage.isNotEmpty()){
                                 onMessage(inputMessage)
                        inputMessage=""
              }
                        },
              modifier = Modifier.size(50.dp)
          ) {
              Icon(Icons.Default.Send, contentDescription = null , modifier = Modifier.size(35.dp))
          }


     }

 }

@Composable
fun AppHeader(){
    Box(
        modifier = Modifier.fillMaxWidth().background(color = colorResource(R.color.lightblue
        )).height(70.dp),

    ){

        Text("Easy Bot",
            modifier = Modifier.padding(start = 10.dp , top  = 15.dp),
             color = Color.Black,
             fontSize = 20.sp,
             fontWeight = FontWeight.Bold,

        )
    }
}


