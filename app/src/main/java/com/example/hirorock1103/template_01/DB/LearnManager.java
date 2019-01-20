package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.Learn;
import com.example.hirorock1103.template_01.Common.Common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LearnManager extends MyDbHelper {

    private String[] statusStr = {"未設定","完了","疑問","疑問→解決"};//0:未設定, 1:完了,2:疑問,3:疑問→解決

    public LearnManager(Context context) {
        super(context);
    }


    //select
    public List<Learn> getList(){

        List<Learn> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_LEARN + " ORDER BY " + LEARN_COLUMN_ID + " ASC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Learn learn = new Learn();
            learn.setId(c.getInt(c.getColumnIndex(LEARN_COLUMN_ID)));
            learn.setLearnTitle(c.getString(c.getColumnIndex(LEARN_COLUMN_TITLE)));
            learn.setCreatedate(c.getString(c.getColumnIndex(LEARN_COLUMN_CREATEDATE)));

            list.add(learn);

            c.moveToNext();
        }


        return list;

    }

    //select by id
    public Learn getListById(int id){

        Learn learn = new Learn();


        String query = "SELECT * FROM " + TABLE_LEARN + " WHERE " + LEARN_COLUMN_ID + " = " + id;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            learn.setId(c.getInt(c.getColumnIndex(LEARN_COLUMN_ID)));
            learn.setLearnTitle(c.getString(c.getColumnIndex(LEARN_COLUMN_TITLE)));
            learn.setCreatedate(c.getString(c.getColumnIndex(LEARN_COLUMN_CREATEDATE)));

            c.moveToNext();
        }


        return learn;

    }


    //add
    public long addLearn(Learn learn){

        ContentValues values = new ContentValues();
        values.put(LEARN_COLUMN_TITLE, learn.getLearnTitle());
        values.put(LEARN_COLUMN_STATUS, learn.getStatus());
        values.put(LEARN_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        long insertId = db.insert(TABLE_LEARN,null,values);

        return insertId;
    }

    //update
    public long update(Learn learn){

        ContentValues values = new ContentValues();
        values.put(LEARN_COLUMN_TITLE, learn.getLearnTitle());
        values.put(LEARN_COLUMN_STATUS, learn.getStatus());

        SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[]{String.valueOf(learn.getId())};

        long insertId = db.update(TABLE_LEARN, values, LEARN_COLUMN_ID + " = ?", args);

        return insertId;

    }


    //delete
    public void delete(int id){
        String query = "DELETE FROM " + TABLE_LEARN + " WHERE " + LEARN_COLUMN_ID + " = " + id;
        SQLiteDatabase db  =getWritableDatabase();
        db.execSQL(query);
    }

    //変換
    public String convertStatusName(int status){

        for (int i = 0; i < this.statusStr.length; i++){
            if(status == i ){
                return this.statusStr[i];
            }
        }

        return null;
    }

    //by status
    public int getEachCountByStatus(int ankenId, int status){
        int count = 0;



        return count;
    }
}
