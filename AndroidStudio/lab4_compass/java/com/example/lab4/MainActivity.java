package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView ivDynamic;
    private TextView tvDegree;
    private float current_degree = 0f;
    //сенсор-менеджер телефона (когда поворачиваешь телефон):
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() { //инициализация переменых
        ivDynamic = findViewById(R.id.ivDynamic);
        tvDegree = findViewById(R.id.tvDegree);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    //ниже метод, который вызывается когда приложение заупускатся или после onPause():
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(
                this, //передать MainActivity, который унаследован от SensorEventListener
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), //нужен сенсор, который отвечает за ориентацию экрана
            sensorManager.SENSOR_DELAY_GAME); //задержка
    }
    //ниже метод, который вызывается когда приложение остановлено (например, позвонили):
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); //перестать получать инф-ию от сенсора
    }
    //переопределение двух методов класса SensorEventListener:
    @Override
    public void onSensorChanged(SensorEvent event) {
        //получить изменение угла от сенсора (event.values[0]):
        float degree = Math.round(event.values[0]);
        //записать в TextView эти градусы:
        tvDegree.setText("Heading: " + Float.toString(degree) + " degrees");

        RotateAnimation ra = new RotateAnimation(
                current_degree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        ra.setDuration(210); //время поворота
        ra.setFillAfter(true);

        ivDynamic.startAnimation(ra);
        current_degree = -degree;
    }
    //без переопредения этого метода возникнет конфликт:
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}