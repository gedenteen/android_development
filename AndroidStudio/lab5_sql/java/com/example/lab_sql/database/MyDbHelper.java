package com.example.lab_sql.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    //вспомогательный класс, который наследуется от SQLiteOpenHelper и меняент некоторые методы
    public MyDbHelper(@Nullable Context context) { //конструктор класса
        super(context, MyConstants.DATABASE_NAME, null, MyConstants.DATABASE_VERSION);
        //"super" для вызова конструктора родительского класса с переданнами в скобках параметрами
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyConstants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //если новая версия таблицы, то удалить базу данных и создать заново
        db.execSQL(MyConstants.DELETE_TABLE);
        onCreate(db);
    }
}
