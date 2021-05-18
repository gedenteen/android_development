package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private logic calculator;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] numberIds = new int[] {
                R.id.zero, R.id.one, R.id.two,
                R.id.three, R.id.four, R.id.five,
                R.id.six, R.id.seven, R.id.eight,
                R.id.nine
        };
        int[] actionsIds = new int[] {
                R.id.sum, R.id.sub, R.id.mul, R.id.div,
                R.id.equals, R.id.clear
        };
        //создать класс:
        calculator = new logic();
        text = findViewById(R.id.text);
        //установка обработчиков нажатий на кнопки:
        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onNumPressed(view.getId());
                text.setText(calculator.getText()); //обновить текст
            }
        };
        View.OnClickListener actionButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onActionPressed(view.getId());
                text.setText(calculator.getText()); //обновить текст
            }
        };
        //добавить обработчики событий по нажатию на кнопки:
        for (int i = 0; i < numberIds.length; i++) {
            findViewById(numberIds[i]).setOnClickListener(numberButtonClickListener);
        }
        for (int i = 0; i < actionsIds.length; i++) {
            findViewById(actionsIds[i]).setOnClickListener(actionButtonClickListener);
        }
    }

}