package com.example.widmetall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Document doc;
    private Thread secThread;
    private Runnable runnable; //Runnable - класс для потока

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    private void init(){

        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();


    }
    private void getWeb(){
        try {

            doc = Jsoup.connect("https://www.cbr.ru/hd_base/metall/metall_base_new/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element table = tables.first();
            Elements el = table.children();
            Element str = el.get(1);
            Elements strEl = str.children();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView txt = (TextView)findViewById(R.id.textGold);
                    txt.setText(strEl.get(1).text());
                    txt = (TextView)findViewById(R.id.textSil);
                    txt.setText(strEl.get(2).text());
                    txt = (TextView)findViewById(R.id.textPla);
                    txt.setText(strEl.get(3).text());
                    txt = (TextView)findViewById(R.id.textPall);
                    txt.setText(strEl.get(4).text());

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}