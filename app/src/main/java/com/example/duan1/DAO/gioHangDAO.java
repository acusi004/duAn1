package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.DbHelper;
import com.example.duan1.model.gioHang;

import java.util.ArrayList;

public class gioHangDAO {
    DbHelper dbHelper;
    SQLiteDatabase database;

    public gioHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean addMon(gioHang gh){
        database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MAGIOHANG",gh.getMaGioHang());
        cv.put("MAMONAN",gh.getMaMonAn());
        cv.put("TENMONAN", gh.getTenMon());
        cv.put("GIA", gh.getGia());
        cv.put("SOLUONG", gh.getSoLuong());
        long result = database.insert("GIOHANG", null, cv);
        if(result == -1){
            return  false;
        }else {
            return true;
        }

    }

    public ArrayList<gioHang> getAll(){
        ArrayList<gioHang> list = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT gh.magiohang, ma.mamonan, ma.tenmon, ma.soluong, ma.giamon FROM GIOHANG gh, MONAN ma WHERE gh.mamonan = ma.mamonan", null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
               list.add(new gioHang(cursor.getInt(0),
                       cursor.getInt(1),
                       cursor.getString(2),
                       cursor.getInt(3),
                       cursor.getInt(4)));
            }while (cursor.moveToNext());
        }

        return list;

    }



    public boolean deleteGioHang(int id){
        database = dbHelper.getWritableDatabase();
        int row = database.delete("GIOHANG", "magiohang =?", new String[]{String.valueOf(id)});
        return row != -1;
    }
}