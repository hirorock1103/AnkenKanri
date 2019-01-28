package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.MileStone;
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

        String query = "SELECT * FROM " + TABLE_ANKEN_NAME + " ORDER BY " + ANKEN_COLUMN_ID + " ASC ";


        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Anken anken = new Anken();
            anken.setId(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ID)));
            anken.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));
            //anken.setAnkenType(c.getInt(c.getColumnIndex(ANKEN_COLUMN_TYPE)));
            anken.setAnkenTypeName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TYPENAME)));
            anken.setBudget(c.getInt(c.getColumnIndex(ANKEN_COLUMN_BUDGET)));
            anken.setStartDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_START)));
            anken.setEndDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_END)));
            anken.setManDay(c.getFloat(c.getColumnIndex(ANKEN_COLUMN_MANDAY)));
            anken.setPrice(c.getInt(c.getColumnIndex(ANKEN_COLUMN_PRICE)));
            anken.setComplete(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ISCOMPLETE)));
            anken.setCreatedate(c.getString(c.getColumnIndex(ANKEN_COLUMN_CREATEDATE)));
            list.add(anken);
            c.moveToNext();
        }

        return list;

    }

    //select
    public List<Anken> getListByIsComplete(int status){

        List<Anken> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ANKEN_NAME
                + " WHERE " + ANKEN_COLUMN_ISCOMPLETE + " = " + status
                + " ORDER BY " + ANKEN_COLUMN_ID + " ASC ";


        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Anken anken = new Anken();
            anken.setId(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ID)));
            anken.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));
            //anken.setAnkenType(c.getInt(c.getColumnIndex(ANKEN_COLUMN_TYPE)));
            anken.setAnkenTypeName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TYPENAME)));
            anken.setBudget(c.getInt(c.getColumnIndex(ANKEN_COLUMN_BUDGET)));
            anken.setStartDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_START)));
            anken.setEndDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_END)));
            anken.setManDay(c.getFloat(c.getColumnIndex(ANKEN_COLUMN_MANDAY)));
            anken.setPrice(c.getInt(c.getColumnIndex(ANKEN_COLUMN_PRICE)));
            anken.setComplete(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ISCOMPLETE)));
            anken.setCreatedate(c.getString(c.getColumnIndex(ANKEN_COLUMN_CREATEDATE)));
            list.add(anken);
            c.moveToNext();
        }

        return list;

    }

    //select
    public List<Anken> getList(String mode){

        List<Anken> list = new ArrayList<>();

        String query;
        if(mode == "finished"){
            query = "SELECT * FROM " + TABLE_ANKEN_NAME + " WHERE " +ANKEN_COLUMN_ISCOMPLETE+ " = 1 ORDER BY " + ANKEN_COLUMN_ID + " ASC ";
        }else{
            query = "SELECT * FROM " + TABLE_ANKEN_NAME + " WHERE " +ANKEN_COLUMN_ISCOMPLETE+ " = 0 ORDER BY " + ANKEN_COLUMN_ID + " ASC ";
        }



        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Anken anken = new Anken();
            anken.setId(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ID)));
            anken.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));
            //anken.setAnkenType(c.getInt(c.getColumnIndex(ANKEN_COLUMN_TYPE)));
            anken.setAnkenTypeName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TYPENAME)));
            anken.setBudget(c.getInt(c.getColumnIndex(ANKEN_COLUMN_BUDGET)));
            anken.setStartDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_START)));
            anken.setEndDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_END)));
            anken.setManDay(c.getFloat(c.getColumnIndex(ANKEN_COLUMN_MANDAY)));
            anken.setPrice(c.getInt(c.getColumnIndex(ANKEN_COLUMN_PRICE)));
            anken.setComplete(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ISCOMPLETE)));
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
            //anken.setAnkenType(c.getInt(c.getColumnIndex(ANKEN_COLUMN_TYPE)));
            anken.setAnkenTypeName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TYPENAME)));
            anken.setBudget(c.getInt(c.getColumnIndex(ANKEN_COLUMN_BUDGET)));
            anken.setStartDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_START)));
            anken.setEndDate(c.getString(c.getColumnIndex(ANKEN_COLUMN_END)));
            anken.setManDay(c.getFloat(c.getColumnIndex(ANKEN_COLUMN_MANDAY)));
            anken.setPrice(c.getInt(c.getColumnIndex(ANKEN_COLUMN_PRICE)));
            anken.setComplete(c.getInt(c.getColumnIndex(ANKEN_COLUMN_ISCOMPLETE)));
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
        //values.put(ANKEN_COLUMN_TYPE, anken.getAnkenType());
        values.put(ANKEN_COLUMN_TYPENAME, anken.getAnkenTypeName());
        values.put(ANKEN_COLUMN_START, anken.getStartDate());
        values.put(ANKEN_COLUMN_END, anken.getEndDate());
        values.put(ANKEN_COLUMN_MANDAY, anken.getManDay());
        values.put(ANKEN_COLUMN_ISCOMPLETE, anken.isComplete());
        values.put(ANKEN_COLUMN_PRICE, anken.getPrice());
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
        //values.put(ANKEN_COLUMN_TYPE, anken.getAnkenType());
        values.put(ANKEN_COLUMN_TYPENAME, anken.getAnkenTypeName());
        values.put(ANKEN_COLUMN_START, anken.getStartDate());
        values.put(ANKEN_COLUMN_END, anken.getEndDate());
        values.put(ANKEN_COLUMN_MANDAY, anken.getManDay());
        values.put(ANKEN_COLUMN_PRICE, anken.getPrice());
        values.put(ANKEN_COLUMN_ISCOMPLETE,anken.isComplete());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = new String[]{String.valueOf(anken.getId())};
        long insertId = db.update(TABLE_ANKEN_NAME, values, ANKEN_COLUMN_ID + " = ?", args);

        return insertId;

    }

    //add milestone
    public long addMilestone(MileStone mileStone){

        ContentValues values = new ContentValues();
        values.put(MILESTONE_COLUMN_NAME, mileStone.getName());
        values.put(MILESTONE_COLUMN_DETAIL, mileStone.getDetail());
        values.put(MILESTONE_COLUMN_ANKENID, mileStone.getAnkenId());
        values.put(MILESTONE_COLUMN_ENDDATE, mileStone.getEndDate());
        values.put(MILESTONE_COLUMN_STATUS,mileStone.getStatus());
        values.put(MILESTONE_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        long insertId = db.insert(TABLE_MILESTONE, null, values);

        return insertId;

    }

    //get milestones
    public List<MileStone> getMilestoneByAnkenId(int ankenId){

        List<MileStone> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_MILESTONE + " WHERE " + MILESTONE_COLUMN_ANKENID + " = " + ankenId + " ORDER BY " + MILESTONE_COLUMN_ENDDATE + " ASC ";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            MileStone mileStone = new MileStone();

            mileStone.setId(c.getInt(c.getColumnIndex(MILESTONE_COLUMN_ID)));
            mileStone.setName(c.getString(c.getColumnIndex(MILESTONE_COLUMN_NAME)));
            mileStone.setDetail(c.getString(c.getColumnIndex(MILESTONE_COLUMN_DETAIL)));
            mileStone.setAnkenId(c.getInt(c.getColumnIndex(MILESTONE_COLUMN_ANKENID)));
            mileStone.setEndDate(c.getString(c.getColumnIndex(MILESTONE_COLUMN_ENDDATE)));
            mileStone.setStatus(c.getInt(c.getColumnIndex(MILESTONE_COLUMN_STATUS)));
            mileStone.setCreatedate(c.getString(c.getColumnIndex(MILESTONE_COLUMN_CREATEDATE)));

            list.add(mileStone);

            c.moveToNext();
        }

        return list;

    }

    //get milestones
    public MileStone getMilestoneByMileStoneId(int milestone){

        MileStone mileStone = new MileStone();

        String query = "SELECT * FROM " + TABLE_MILESTONE + " WHERE " + MILESTONE_COLUMN_ID + " = " + milestone ;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            mileStone.setId(c.getInt(c.getColumnIndex(MILESTONE_COLUMN_ID)));
            mileStone.setName(c.getString(c.getColumnIndex(MILESTONE_COLUMN_NAME)));
            mileStone.setDetail(c.getString(c.getColumnIndex(MILESTONE_COLUMN_DETAIL)));
            mileStone.setAnkenId(c.getInt(c.getColumnIndex(MILESTONE_COLUMN_ANKENID)));
            mileStone.setEndDate(c.getString(c.getColumnIndex(MILESTONE_COLUMN_ENDDATE)));
            mileStone.setStatus(c.getInt(c.getColumnIndex(MILESTONE_COLUMN_STATUS)));
            mileStone.setCreatedate(c.getString(c.getColumnIndex(MILESTONE_COLUMN_CREATEDATE)));

            c.moveToNext();
        }

        return mileStone;

    }


    //update mileston
    public long updateMilestone(MileStone mileStone){

        ContentValues values = new ContentValues();
        values.put(MILESTONE_COLUMN_NAME, mileStone.getName());
        values.put(MILESTONE_COLUMN_ANKENID, mileStone.getAnkenId());
        values.put(MILESTONE_COLUMN_DETAIL, mileStone.getDetail());
        values.put(MILESTONE_COLUMN_ENDDATE, mileStone.getEndDate());
        values.put(MILESTONE_COLUMN_STATUS,mileStone.getStatus());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = new String[]{String.valueOf(mileStone.getId())};

        long insertId = db.update(TABLE_MILESTONE, values, MILESTONE_COLUMN_ID + " = ?",args);

        return insertId;

    }

    //delete milestone
    public void deleteMilestone(int milestoneId){
        String query = "DELETE FROM " + TABLE_MILESTONE + " WHERE " + MILESTONE_COLUMN_ID + " = " + milestoneId;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }


}
