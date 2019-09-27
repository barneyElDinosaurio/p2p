package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
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

import com.github.bassaer.chatmessageview.view.ChatView;


public class MainActivity extends AppCompatActivity implements USBSerialListener{

    USBSerialConnector mConnector;

    String data;


    //EditText txText;
    TextView rxText,userText;
    //Button send,get;

    String texto = "";
    ActionBar mAB;

    ChatView chatView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        mAB = getSupportActionBar();

        mConnector = USBSerialConnector.getInstance();

        //txText = (EditText) findViewById(R.id.txText);
        //txText.setEnabled(false);

        //userText = (TextView) findViewById(R.id.usertext);
        //userText.setVisibility(View.GONE);//hiden help

        //creating chat view
        chatView = (ChatView) findViewById(R.id.chat_view);

        //    rxText = (TextView) findViewById(R.id.rxText);

      //  rxText.setMovementMethod(new ScrollingMovementMethod());
        //txText.setEnabled(false);

        //send = (Button) findViewById(R.id.btn1);
        //send.setEnabled(false);
        //send.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {

                //Serial write
              //  mConnector.mWrite("escr"+txText.getText().toString());
                //txText.setText("");


            //}
        //});


        //get = (Button) findViewById(R.id.leerBtn);
        //get.setEnabled(false);
        //get.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //   mConnector.mWrite("leer");
            //}
        //});

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

                //rxText.setText(rxText.getText()+Utilities.bytesToString(data) + "\n");
                texto = texto + Utilities.bytesToString(data);//Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();

                //////////////////////////////CONDICIONES///////////////////////////////////////////////


            if(texto.contains("buffer") ) {
                Toast.makeText(this, "Splitiando mensaje", Toast.LENGTH_SHORT).show();
                msg = texto.split("buffer");
                if (msg.length > 1){
                    //Toast.makeText(this, msg[1], Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, msg[1].split("mensajeRecibido")[0], Toast.LENGTH_SHORT).show();
                    userText.setText("");
                    userText.setText(msg[1].split("mensajeRecibido")[0]);
                    mAB.setTitle(msg[1].split("mensajeRecibido")[0]);

                    SystemClock.sleep(2000);
                }
                //texto = "";
            }

                //Inicio de dispositivo
                if(texto.contains("Suceso")) {
                    Toast.makeText(this, "Dispositivo Iniciado", Toast.LENGTH_SHORT).show();
//                    userText.setText("");
  //                  userText.setText("Dispositivo Iniciado");
                    mAB.setTitle("Dispositivo Iniciado");
                    SystemClock.sleep(2000);
                    texto = "";
                }

            //Inicio de dispositivo
            if(texto.contains("retry")) {
                Toast.makeText(this, "Intentando de nuevo", Toast.LENGTH_SHORT).show();
                //userText.setText("");
                //userText.setText("Intentando de nuevo .. ");
                mAB.setTitle("Intentando de nuevo ..");

                SystemClock.sleep(2000);
                texto = "";
            }

                //Leyendo
                if(texto.contains("leyendo")) {
                    Toast.makeText(this, "Leyendo mensajes ...", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Leyendo mensajes ...");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                //Escribiendo
                if(texto.contains("escribiendo")) {
                    Toast.makeText(this, "Intentando escribir mensaje ...", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Intentando escribir mensaje ...");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                //Mensaje enviado
                if(texto.contains("MensajEnviado")) {
                    Toast.makeText(this, "Mensaje enviado...", Toast.LENGTH_LONG).show();
                    mAB.setTitle("Mensaje enviado...");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                //Mensaje recibido
                if(texto.contains("mensajeRecibido")) {
                    Toast.makeText(this, "Mensajes recibidos . .", Toast.LENGTH_LONG).show();
                    mAB.setTitle("Intentando de nuevo ..");
                    SystemClock.sleep(2000);
                    texto = "";
                }


                //Error al enviar
                if(texto.contains("sendSBDTextFailed")) {
                    Toast.makeText(this, "ERROR al enviar mensaje!!! X!", Toast.LENGTH_LONG).show();
                    mAB.setTitle("ERROR al enviar mensaje!!! X!");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                //Error al recibir
                if(texto.contains("ReceiveSBDTextfailed")) {
                    Toast.makeText(this, "ERROR al intentar leer el mensaje!!", Toast.LENGTH_LONG).show();
                    mAB.setTitle("ERROR al intentar leer el mensaje!!");
                    SystemClock.sleep(2000);
                    texto = "";
                }


                //Identificadores de señaL


            //Identificadores de señaL
                if(texto.contains("SignalQualityfailed")) {
                    Toast.makeText(this, "Error de señal", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Error de señal");
                    SystemClock.sleep(2000);
                    texto = "";
                }


                if(texto.contains("CSQ:0")) {
                    Toast.makeText(this, "Sin Señal", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Sin Señal");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                if(texto.contains("CSQ:1")) {
                    Toast.makeText(this, "Señal 1", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 1 ");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                if(texto.contains("CSQ:2")) {
                    Toast.makeText(this, "Señal 2", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 2");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                if(texto.contains("CSQ:3")) {
                    Toast.makeText(this, "Señal 3", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 3");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                if(texto.contains("CSQ:4")) {
                    Toast.makeText(this, "Señal 4", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 4");
                    SystemClock.sleep(2000);
                    texto = "";
                }

                if(texto.contains("CSQ:5")) {
                    Toast.makeText(this, "Señal 5", Toast.LENGTH_SHORT).show();
                    mAB.setTitle("Señal 5");
                    SystemClock.sleep(2000);
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
        //txText.setEnabled(true);
        //rxText.setEnabled(true);
        //send.setEnabled(true);
        //get.setEnabled(true);
    }

    @Override
    public void onDeviceDisconnected() {


//        Toast.makeText(this, String.valueOf(msg.length), Toast.LENGTH_SHORT).show();
//        for(String spliteo:msg) Toast.makeText(this, spliteo, Toast.LENGTH_SHORT).show();
        finish();
        
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
