package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Common.Common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnkenManager extends MyDbHelper {
    public AnkenManager(Context context) {
        super(context);
    }

    //select
    public List<Anken> getList(){

        List<Anken> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ANKEN_NAME + " ORDER BY " + ANKEN_COLUMN_ID + " DESC ";

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Anken anken = new Anken();
            anken.setId(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ID)));
            anken.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));
            anken.setAnkenType(c.getInt(c.getColumnIndex(ANKEN_COLUMN_TYPE)));
            anken.setBudget(c.getInt(c.getColumnIndex(ANKEN_COLUMN_BUDGET)));
            anken.setStartDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_START)));
            anken.setEndDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_END)));
            boolean result = (c.getInt(c.getColumnIndex(ANKEN_COLUMN_ISCOMPLETE)) == 0) ? false : true;
            anken.setComplete(result);
            anken.setCreatedate(c.getString(c.getColumnIndex(ANKEN_COLUMN_CREATEDATE)));
            list.add(anken);
            c.moveToNext();
        }

        return list;

    }

    //select by id
    public Anken getListByID(int id){

        Anken anken = new Anken();
        String query = "SELECT * FROM " + TABLE_ANKEN_NAME + " WHERE " + ANKEN_COLUMN_ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            anken.setId(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ID)));
            anken.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));
            anken.setAnkenType(c.getInt(c.getColumnIndex(ANKEN_COLUMN_TYPE)));
            anken.setBudget(c.getInt(c.getColumnIndex(ANKEN_COLUMN_BUDGET)));
            anken.setStartDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_START)));
            anken.setEndDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_END)));
            boolean result = (c.getInt(c.getColumnIndex(ANKEN_COLUMN_ISCOMPLETE)) == 0) ? false : true;
            anken.setComplete(result);
            anken.setCreatedate(c.getString(c.getColumnIndex(ANKEN_COLUMN_CREATEDATE)));

            c.moveToNext();
        }

        return anken;

    }

    //delete
    public void delete(int id){

        String query = "DELETE FROM " + TABLE_ANKEN_NAME + " WHERE " + ANKEN_COLUMN_ID + " = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }

    //add
    public long addAnken(Anken anken){

        ContentValues values = new ContentValues();
        values.put(ANKEN_COLUMN_TITLE, anken.getAnkenName());
        values.put(ANKEN_COLUMN_BUDGET, anken.getBudget());
        values.put(ANKEN_COLUMN_TYPE, anken.getAnkenType());
        values.put(ANKEN_COLUMN_START, anken.getStartDate());
        values.put(ANKEN_COLUMN_END, anken.getEndDate());
        values.put(ANKEN_COLUMN_ISCOMPLETE, anken.isComplete());
        values.put(ANKEN_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();

        long insertId = db.insert(TABLE_ANKEN_NAME, null, values);

        return insertId;

    }

    //update
    public long update(Anken anken){

        ContentValues values = new ContentValues();
        values.put(ANKEN_COLUMN_TITLE, anken.getAnkenName());
        values.put(ANKEN_COLUMN_BUDGET, anken.getBudget());
        values.put(ANKEN_COLUMN_TYPE, anken.getAnkenType());
        values.put(ANKEN_COLUMN_START, anken.getStartDate());
        values.put(ANKEN_COLUMN_END, anken.getEndDate());
        values.put(ANKEN_COLUMN_ISCOMPLETE, anken.isComplete());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = new String[]{String.valueOf(anken.getId())};
        long insertId = db.update(TABLE_ANKEN_NAME, values, ANKEN_COLUMN_ID + " = ?", args);

        return insertId;

    }

}
