package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.view.MessageView;

import java.util.ArrayList;
import java.util.Random;

public class Main2 extends AppCompatActivity {

    ChatView chatView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        //creating chat view
        chatView = (ChatView) findViewById(R.id.chat_view);

        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "Marco";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = "El flaco";

        final ChatUser me = new ChatUser(myId, myName, myIcon);
        final ChatUser you = new ChatUser(yourId, yourName, yourIcon);

        //settings msgs
        Message message1 = new Message.Builder()
                .setUser(me) // Sender
                .setRight(true) // This message Will be shown right side.
                .setText("Mijo!") //Message contents
                .build();
        Message message2 = new Message.Builder()
                .setUser(you) // Sender
                .setRight(false) // This message Will be shown left side.
                .setText("Ya picamos al gordo señor") //Message contents
                .build();

        //add msgs
        chatView.send(message1);
        chatView.receive(message2);
        }


}