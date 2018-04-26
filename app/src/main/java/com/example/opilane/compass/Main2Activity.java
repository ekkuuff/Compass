package com.example.opilane.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    //deklareerime muutujad
    private ImageView kompass;
    TextView suund;

    //salvestame kompassipildi pöördenurga
    private float algsedKraadid = 0f;

    //seadme sensor manager
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //lähtestame muutujad
        kompass = findViewById(R.id.kompass);
        suund = findViewById(R.id.nurk);
        //android seadme andurite funktsioonide lähtestamine
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //süsteemi orientatsioon sensor, registreerib muudatusi
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //lõpetame registreeringute kuulamise
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //saame nurga/kraadid z-teljelt kui seda pööratud
        float kraadinurk = Math.round(event.values[0]);
        //kuvame oma textviewdis toimunud kraadi muudatuse
        suund.setText("Suund: " + Float.toString(kraadinurk) + " kraadi");
        //loome pöörlemis animatsiooni
        RotateAnimation pöörlemisAnimatsioon = new RotateAnimation(algsedKraadid, -kraadinurk, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //defineerime animatsiooni kestvuse
        pöörlemisAnimatsioon.setDuration(200);
        pöörlemisAnimatsioon.setFillAfter(true);
        kompass.startAnimation(pöörlemisAnimatsioon);
        //salvestame uued kraadid algsete asemel
        algsedKraadid =-kraadinurk;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}