package com.example.lab_sql.database;

public class MyConstants {
    //это класс для строковых констант, которые задают те или иные названия в базе данных
    public static final String DATABASE_NAME = "my_database.db"; //название базы данных
    public static final int DATABASE_VERSION = 2; //версия базы данных - при каждом измении в БД
    // надо менять версию
    public static final String TABLE_NAME = "table_students"; //название таблицы
    public static final String S_ID = "s_id"; //1-ая колонка в таблице = айди
    public static final String S_NAME = "s_name"; //2-ая колонка в таблице = имя студента
    public static final String S_WEIGHT = "s_weight"; //3 колонка в таблице = вес
    public static final String S_HEIGHT = "s_height"; //4 колонка в таблице = рост
    public static final String S_AGE = "s_age"; //5 колонка в таблице = возраст
    // ниже команда, которая создает таблицу в БД (если таблицы с таким именем еще нет)
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            S_ID + " INTEGER PRIMARY KEY," +
            S_NAME + " TEXT," +
            S_WEIGHT + " INTEGER," +
            S_HEIGHT + " INTEGER," +
            S_AGE + " INTEGER)";
    // удаление (drop) таблицы:
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
