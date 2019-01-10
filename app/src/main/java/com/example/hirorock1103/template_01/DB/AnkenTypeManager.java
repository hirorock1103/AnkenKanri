package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.AnkenType;

import java.util.ArrayList;
import java.util.List;

public class AnkenTypeManager extends MyDbHelper {
    public AnkenTypeManager(Context context) {
        super(context);
    }

    //add type
    public long addType(AnkenType type){

        ContentValues values = new ContentValues();
        values.put(TYPE_COLUMN_TYPENAME, type.getTypeName());
        values.put(TYPE_COLUMN_STATUS, type.getStatus());

        SQLiteDatabase db = getWritableDatabase();

        long insertId = db.insert(TABLE_ANKEN_TYPE, null, values);

        return insertId;

    }

    //get type
    public List<AnkenType> getList(){
        String query = "SELECT * FROM " + TABLE_ANKEN_TYPE + " ORDER BY " + ANKEN_COLUMN_ID + " DESC ";
        SQLiteDatabase db = getWritableDatabase() ;

        List<AnkenType> list = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            AnkenType type = new AnkenType();

            type.setId(c.getInt(c.getColumnIndex(TYPE_COLUMN_ID)));
            type.setTypeName(c.getString(c.getColumnIndex(TYPE_COLUMN_TYPENAME)));
            type.setStatus(c.getInt(c.getColumnIndex(TYPE_COLUMN_STATUS)));

            list.add(type);

            c.moveToNext();
        }

        return list;

    }

    //delete type
    public void delete(int id){

        String query ="DELETE FROM " + TABLE_ANKEN_TYPE + " WHERE " + TYPE_COLUMN_ID + " = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }

    //update tyep
    public long update(AnkenType type){
        ContentValues values = new ContentValues();
        values.put(TYPE_COLUMN_TYPENAME, type.getTypeName());
        values.put(TYPE_COLUMN_STATUS, type.getStatus());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = new String[]{String.valueOf(type.getId())};

        long insertId = db.update(TABLE_ANKEN_TYPE, values, TYPE_COLUMN_ID + " = ? ", args);

        return insertId;

    }



}
