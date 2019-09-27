package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;


public class MainActivity extends AppCompatActivity implements USBSerialListener{

    USBSerialConnector mConnector;

    String texto = "";
    ActionBar mAB;

    ChatView chatView;


    //Users///////////////////////
    //User id
    int myId ;
    //User icon
    Bitmap myIcon;
    //User name
    String myName;

    int yourId;
    Bitmap yourIcon ;
    String yourName ;

    ChatUser me;
    ChatUser you;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);


        mAB = getSupportActionBar();
        mAB.setTitle("p2p");

        mConnector = USBSerialConnector.getInstance();

        //Users///////////////////////
        //User id
        myId = 0;
        //User icon
         myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
         myName = "Marco";

         yourId = 1;
         yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
         yourName = "El flaco";

         me = new ChatUser(myId, myName, myIcon);
         you = new ChatUser(yourId,yourName, yourIcon);;


        //creating chat view
        chatView = (ChatView) findViewById(R.id.chat_view);



        chatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnector.mWrite("escr"+chatView.getInputText());


            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();
        mConnector.setUsbSerialListener(this);
        mConnector.init(this,9600);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnector.pausedActivity();
    }


    String msg[];
    //Serial Receiver Methods
    @Override
    public void onDataReceived(byte[] data) {
        if(data != null)    {

            texto = texto + Utilities.bytesToString(data);

            //////////////////////////////CONDICIONES///////////////////////////////////////////////

            if(texto.contains("buffer") ) {
                //Toast.makeText(this, "Splitiando mensaje", Toast.LENGTH_SHORT).show();

            }

            //Inicio de dispositivo
            if(texto.contains("Suceso")) {
                mAB.setTitle("Dispositivo Iniciado");
                //SystemClock.sleep(500);
                texto = "";
            }

            //Inicio de dispositivo
            if(texto.contains("retry")) {
                mAB.setTitle("Intentando de nuevo ..");
                //SystemClock.sleep(500);
                texto = "";
            }

                //Leyendo
                if(texto.contains("leyendo")) {
                    //Toast.makeText(this, "Leyendo mensajes ...", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Leyendo mensajes ...");
                    //SystemClock.sleep(500);
                    texto = "";
                }

                //Escribiendo
                if(texto.contains("escribiendo")) {
                    //Toast.makeText(this, "Intentando escribir mensaje ...", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Intentando escribir mensaje ...");
                    //SystemClock.sleep(500);
                    texto = "";
                }

                //Mensaje enviado
                if(texto.contains("MensajEnviado")) {
                    Toast.makeText(this, "Mensaje enviado...", Toast.LENGTH_LONG).show();
                    mAB.setTitle("Mensaje enviado...");
                    chatView.send(new Message.Builder().setUser(me).setRight(true).setText(chatView.getInputText()).build());
                    chatView.setInputText("");
                    //SystemClock.sleep(500);
                    texto = "";
                }

                //Mensaje recibido
                if(texto.contains("mensajeRecibido")) {
                    //Toast.makeText(this, "Mensajes recibidos . .", Toast.LENGTH_LONG).show();
                    msg = texto.split("buffer");
                    if (msg.length > 1){

                        chatView.receive(new Message.Builder().setUser(you).setRight(false).setText(msg[1].split("mensajeRecibido")[0]).build());
                        //SystemClock.sleep(500);
                    }
                    mAB.setTitle("Mensaje Recibido ... ");
                    texto = "";
                }


                //Error al enviar
                if(texto.contains("sendSBDTextFailed")) {
                    Toast.makeText(this, "ERROR al enviar mensaje!!! X!", Toast.LENGTH_LONG).show();
                    mAB.setTitle("ERROR al enviar mensaje!!! X!");
                    //SystemClock.sleep(500);
                    texto = "";
                }

                //Error al recibir
                if(texto.contains("ReceiveSBDTextfailed")) {
                    Toast.makeText(this, "ERROR al intentar leer el mensaje!!", Toast.LENGTH_LONG).show();
                    mAB.setTitle("ERROR al intentar leer el mensaje!!");
                    //SystemClock.sleep(500);
                    texto = "";
                }


            //Identificadores de señaL
                if(texto.contains("SignalQualityfailed")) {
                    Toast.makeText(this, "Error de señal", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Error de señal");
                    //SystemClock.sleep(500);
                    texto = "";
                }


                if(texto.contains("CSQ:0")) {
                    Toast.makeText(this, "Sin Señal", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Sin Señal");
                 //   SystemClock.sleep(500);
                    texto = "";
                }

                if(texto.contains("CSQ:1")) {
                    Toast.makeText(this, "Señal 1", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 1 ");
                   // SystemClock.sleep(500);
                    texto = "";
                }

                if(texto.contains("CSQ:2")) {
                    Toast.makeText(this, "Señal 2", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 2");
                    //SystemClock.sleep(500);
                    texto = "";
                }

                if(texto.contains("CSQ:3")) {
                    Toast.makeText(this, "Señal 3", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 3");
                   // SystemClock.sleep(500);
                    texto = "";
                }

                if(texto.contains("CSQ:4")) {
                    Toast.makeText(this, "Señal 4", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 4");
                   // SystemClock.sleep(500);
                    texto = "";
                }

                if(texto.contains("CSQ:5")) {
                    Toast.makeText(this, "Señal 5", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 5");
                    //SystemClock.sleep(500);
                    texto = "";
                }




        }else{
            Toast.makeText(this, "Error en conversion de lectura", Toast.LENGTH_SHORT).show();
            mAB.setTitle("Error en conversion de lectura");


        }

    }

    @Override
    public void onErrorReceived(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeviceReady(ResponseStatus responseStatus) {
        mAB.setTitle("Esperando dispositivo");
        //txText.setEnabled(true);

    }

    @Override
    public void onDeviceDisconnected() {

        mAB.setTitle("Dispositivo desconectado");
        Toast.makeText(this, "Dispositivo desconectado", Toast.LENGTH_SHORT).show();
        //finish();
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                mConnector.mWrite("leer");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


   
}
