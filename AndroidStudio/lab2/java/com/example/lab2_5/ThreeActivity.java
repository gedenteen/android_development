package com.example.lab2_5;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ImageView;

public class ThreeActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three);

        int ROWS = 3;
        int COLS = 2;
        TableLayout tblLayout = findViewById(R.id.tableLayout3);
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < COLS; j++) {
                // получение изображения полки
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.cat_nose);
                tableRow.addView(imageView, j);
                //tableRow.
            }
            tblLayout.addView(tableRow, i);
        }
    }
}
