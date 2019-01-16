package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager extends MyDbHelper {
    public TaskManager(Context context) {
        super(context);
    }

    //select
    public List<Task> getList(){

        List<Task> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TASK + " ORDER BY " + TASK_COLUMN_ID + " ASC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Task task = new Task();
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));
            task.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setDetail(c.getString(c.getColumnIndex(TASK_COLUMN_DETAIL)));
            task.setEndDate(c.getString(c.getColumnIndex(TASK_COLUMN_ENDDATE)));
            task.setStatus(c.getInt(c.getColumnIndex(TASK_COLUMN_STATUS)));
            task.setCreatedate(c.getString(c.getColumnIndex(TASK_COLUMN_CREATEDATE)));

            list.add(task);

            c.moveToNext();
        }


        return list;

    }

    //select by id
    public Task getListById(int id){

        Task task = new Task();

        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + TASK_COLUMN_ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));
            task.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setDetail(c.getString(c.getColumnIndex(TASK_COLUMN_DETAIL)));
            task.setEndDate(c.getString(c.getColumnIndex(TASK_COLUMN_ENDDATE)));
            task.setStatus(c.getInt(c.getColumnIndex(TASK_COLUMN_STATUS)));
            task.setCreatedate(c.getString(c.getColumnIndex(TASK_COLUMN_CREATEDATE)));

            c.moveToNext();
        }


        return task;

    }
    //add
    public long addTask(Task task){

        ContentValues values = new ContentValues();
        values.put(TASK_COLUMN_NAME, task.getTaskName());
        values.put(TASK_COLUMN_DETAIL, task.getDetail());
        values.put(TASK_COLUMN_ANKENID, task.getAnkenId());
        values.put(TASK_COLUMN_STATUS, task.getStatus());
        values.put(TASK_COLUMN_ENDDATE, task.getEndDate());
        values.put(TASK_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        long insertId = db.insert(TABLE_TASK,null,values);

        return insertId;
    }

    //update
    public long update(Task task){

        ContentValues values = new ContentValues();
        values.put(TASK_COLUMN_NAME, task.getTaskName());
        values.put(TASK_COLUMN_DETAIL, task.getDetail());
        values.put(TASK_COLUMN_ANKENID, task.getAnkenId());
        values.put(TASK_COLUMN_STATUS, task.getStatus());
        values.put(TASK_COLUMN_ENDDATE, task.getEndDate());

        SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[]{String.valueOf(task.getId())};

        long insertId = db.update(TABLE_TASK, values, TASK_COLUMN_ID + " = ?", args);

        return insertId;

    }


    //delete
    public void delete(int id){
        String query = "DELETE FROM " + TABLE_TASK + " WHERE " + TASK_COLUMN_ID + " = " + id;
        SQLiteDatabase db  =getWritableDatabase();
        db.execSQL(query);
    }

}
