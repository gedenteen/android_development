package com.example.lab_sql.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Random;
import java.util.Vector;


//класс, для открытия и добавления записей в базу данных
public class MyDbManager {
    //класс, методы которого вызываются в MainActivity, тут все нужные функции для работы с БД
    private Context context;
    private MyDbHelper myDbHelper; //использовать класс, который мы унаследовали от SQLiteOpenHelper
    private SQLiteDatabase sqlite_db; //база данных

    public MyDbManager(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }
    public void openDb() {
        //открыть базу данных для чтения и записи:
        sqlite_db = myDbHelper.getWritableDatabase();
    }
    public void insertToDb() {
        //для добавления записи в БД используются случайные значения
        Random rand = new Random();
        int n = rand.nextInt(5);
        String name; //случайное имя студента
        switch (n) {
            case 0:
                name = "Иванов";
                break;
            case 1:
                name = "Иванова";
                break;
            case 2:
                name = "Вассерман";
                break;
            case 3:
                name = "Канделаки";
                break;
            case 4:
                name = "Якубович";
                break;
            default:
                name = "ошибка в insertToDb()";
                break;
        }

        int weight = rand.nextInt(100); //случайный вес от 50 до 150
        weight += 50;
        int height = rand.nextInt(50); //случайный рост от 150 до 200
        height += 150;
        int age = rand.nextInt(10); //случайный возраст от 18 до 28
        age += 18;
        //нужно создать объект класса ContentValues, записать в него значения
        //и передать этот объект для добавления записи в БД
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.S_NAME, name); //put(ключ, значение)
        cv.put(MyConstants.S_WEIGHT, weight);
        cv.put(MyConstants.S_HEIGHT, height);
        cv.put(MyConstants.S_AGE, age);
        //выбрать в базе данных таблицу и добавить туда созданный ContentValues:
        sqlite_db.insert(MyConstants.TABLE_NAME, null, cv);
    }
    public Vector<String> readSortedDb() {
        //данный метод делает запрос к базе данных, в котором берутся все значения таблицы
        //в отсортированном (по возрасту) порядке
        Vector<String> returnVector = new Vector<>(); //этот массив будет возвращаться
        //Cursor - класс для считывания из БД. Делаем запрос:
        Cursor cursor = sqlite_db.rawQuery("SELECT * FROM " + MyConstants.TABLE_NAME +
                " ORDER BY " + MyConstants.S_AGE, null);
        //в цикле ниже в массив добавляются имя, вес, рост, возраст каждой записи
        while (cursor.moveToNext()) {
            String s = cursor.getString(cursor.getColumnIndex(MyConstants.S_NAME));
            returnVector.add(s);
            s = cursor.getString(cursor.getColumnIndex(MyConstants.S_WEIGHT));
            returnVector.add(s);
            s = cursor.getString(cursor.getColumnIndex(MyConstants.S_HEIGHT));
            returnVector.add(s);
            s = cursor.getString(cursor.getColumnIndex(MyConstants.S_AGE));
            returnVector.add(s);
        }
        cursor.close();
        return returnVector;
    }
    public void closeDb() {
        myDbHelper.close();
    }
    public void clearDb() {
        //очистить базу данных
        myDbHelper.onUpgrade(sqlite_db, -1, -1);
    }

}
