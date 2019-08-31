package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements USBSerialListener{

    USBSerialConnector mConnector;
    UsbDevice device;

    EditText txText;
    TextView rxText;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mConnector = USBSerialConnector.getInstance();
        txText = (EditText) findViewById(R.id.txText);
        txText.setEnabled(false);
        rxText = (TextView) findViewById(R.id.rxText);
        txText.setEnabled(false);

        send = (Button) findViewById(R.id.btn1);
        send.setEnabled(false);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = txText.getText().toString();
                mConnector.writeAsync(data.getBytes());
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

    //Serial Receiver Methods
    @Override
    public void onDataReceived(byte[] data) {
        if(data != null){
                rxText.setText(rxText.getText()+"\n HEX>"+ Utilities.bytesToHex(data)+"\nSTRING>"+Utilities.bytesToString(data) );
        }else{
            Toast.makeText(this, "Error en conversion de lectura", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorReceived(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeviceReady(ResponseStatus responseStatus) {
        txText.setEnabled(true);
        rxText.setEnabled(true);
        send.setEnabled(true);
    }

    @Override
    public void onDeviceDisconnected() {
        Toast.makeText(this, "Dispositivo desconectado", Toast.LENGTH_SHORT).show();
        finish();
    }






}
