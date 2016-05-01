package com.example.kuisfisika;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    final static String DB_NAME = "db_kuis";

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_soal(id INTEGER PRIMARY KEY AUTOINCREMENT, soal TEXT, pil_a TEXT, pil_b TEXT, pil_c TEXT, jwban INTEGER, img BLOB)";
        db.execSQL(sql);

        ContentValues values = new ContentValues();
        values.put("soal", "Bacaan skala yang tepat dari pengukuran diameter benda tersebut adalah?");
        values.put("pil_a", "5,31 mm");
        values.put("pil_b","6,31 mm");
        values.put("pil_c", "7,31 mm");
        values.put("jwban","2");
        values.put("img", R.drawable.satu);
        db.insert("tbl_soal", "soal", values);

        values.put("soal", "Tiga buah gaya F1, F2, dan F3 memiliki arah dan besar seperti pada gambar berikut ini. Hubungan yang benar untuk ketiga gaya tersebut adalah?");
        values.put("pil_a", "F1 + F2 = F3");
        values.put("pil_b","F2 + F3 = F1");
        values.put("pil_c", "F3 + F1= F2");
        values.put("jwban","2");
        values.put("img", R.drawable.dua);
        db.insert("tbl_soal", "soal", values);

        values.put("soal", "Resultan dari ketiga gaya tersebut adalah?");
        values.put("pil_a", "5 satuan");
        values.put("pil_b","8 satuan");
        values.put("pil_c", "13 satuan");
        values.put("jwban","2");
        values.put("img", R.drawable.tiga);
        db.insert("tbl_soal", "soal", values);

        values.put("soal", "Sebuah benda seberat 16.000 N ditempatkan pada penampang A2 seperti gambar. Agar benda tersebut terangkat, maka diperlukan gaya sebesar?");
        values.put("pil_a", "80 N");
        values.put("pil_b","180 N");
        values.put("pil_c", "200 N");
        values.put("jwban","2");
        values.put("img", R.drawable.empat);
        db.insert("tbl_soal", "soal", values);

        values.put("soal", "Seorang anak kesekolah naik sepeda dengan lintasan seperti gambar. Besar perpindahan anak tersebut dari keberangkatannya sampai tiba di sekolah adalah?");
        values.put("pil_a", "300 m");
        values.put("pil_b","400 m");
        values.put("pil_c", "500 m");
        values.put("jwban","2");
        values.put("img", R.drawable.lima);
        db.insert("tbl_soal", "soal", values);

    }

    public List<Soal> getSoal(){
        List<Soal> listSoal = new ArrayList<Soal>();
        String query = "select * from tbl_soal";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Soal s = new Soal();
                s.setSoal(cursor.getString(1));
                s.setPil_a(cursor.getString(2));
                s.setPil_b(cursor.getString(3));
                s.setPil_c(cursor.getString(4));
                s.setJwban(cursor.getInt(5));
                s.setGambar(cursor.getInt(6));
                listSoal.add(s);
            }while(cursor.moveToNext());
        }

        return listSoal;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_soal");
        onCreate(db);

    }

}