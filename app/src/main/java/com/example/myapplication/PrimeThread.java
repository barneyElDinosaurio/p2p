package com.example.myapplication;

import android.util.Log;
import android.widget.Toast;

public class PrimeThread extends Thread {
    long minPrime;
    PrimeThread(long minPrime) {
        this.minPrime = minPrime;
    }

    public void run() {
        // compute primes larger than minPrime
        Log.d("PRUEBA","AQUI JUEEEEEEEEEEEEEEE");
    }
}