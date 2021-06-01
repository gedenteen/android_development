package com.example.lab_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lab_sql.database.MyDbManager;

import java.util.ArrayList;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {
    private MyDbManager myDbManager;
    private TableLayout tableForDb;
    ArrayList<TableRow> rows = new ArrayList<>(); //массив для строк в TableLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDbManager = new MyDbManager(this);
        tableForDb = findViewById(R.id.tableForDb);
        addRowInTableForDb("Фамилия", "вес", "рост", "возраст", 0); //верхняя строка в TableLayout
    }
    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
    }
    @Override
    protected void onDestroy() {
        myDbManager.closeDb();
        super.onDestroy();
    }

    public void onClickAdd(View view) {
        myDbManager.insertToDb();
    }
    public void onClickShowDb(View view) {
        //функция для показа БД в таблице TableLayout
        for (int i = rows.size() - 1; i >= 1; i--) { //удалить все строки кроме самой верхней
            TableRow row = rows.get(i);
            tableForDb.removeView(row);
            rows.remove(i);
        }

        Vector<String> whole_table = myDbManager.readSortedDb();
        int ind_row = 1; //индекс строки
        for (int i = 0; i < whole_table.size(); i += 4) {
            addRowInTableForDb(whole_table.get(i),
                    whole_table.get(i+1),
                    whole_table.get(i+2),
                    whole_table.get(i+3),
                    ind_row++);
        }
    }
    public void onClickClear(View view) {
        myDbManager.clearDb();
    }

    //вспомогательная функция для добавления строки в таблицу TableLayout
    private void addRowInTableForDb(String s1, String s2, String s3, String s4, int ind) {
        TableRow tableRow = new TableRow(this); //создать строку для TableLayout
        rows.add(tableRow); //записать адрес (?) этой строки в rows[]
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        for (int j = 0; j < 4; j++) { //создать 4 TextView - это как бы ячейки в строке
            TextView textView = new TextView(this);
            switch (j) { //установить ширину ячеек и задать текст TextView
                case 0:
                    textView.setText(s1);
                    textView.setWidth(300);
                    break;
                case 1:
                    textView.setText(s2);
                    textView.setWidth(100);
                    break;
                case 2:
                    textView.setText(s3);
                    textView.setWidth(150);
                    break;
                case 3:
                    textView.setText(s4);
                    textView.setWidth(200);
                    break;
            }
            tableRow.addView(textView, j); //добавить textView[j] в строку
        }
        tableForDb.addView(tableRow, ind); //добавить строку в таблицу TableLayout
    }

}