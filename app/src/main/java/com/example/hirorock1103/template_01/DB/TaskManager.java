package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Anken.TaskHistory;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager extends MyDbHelper {

    String[] strList = {"未対応","完了","対応中"};

    public TaskManager(Context context) {
        super(context);
    }

    //arrayname
    public String convertStatusName(int num)
    {

        for (int i = 0; i < this.strList.length; i ++){
            if(i == num){
                return strList[i];
            }
        }

        return null;

    }

    //by status
    public int getEachCountByStatus(int ankenId, int status){
        int count = 0;

        //
        String query = "SELECT COUNT(*) as total FROM " + TABLE_TASK + " WHERE " + TASK_COLUMN_ANKENID + " = " + ankenId + " AND " + TASK_COLUMN_STATUS + " = " + status;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            count = c.getInt(c.getColumnIndex("total"));
            c.moveToNext();
        }

        return count;
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
            task.setId(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setDetail(c.getString(c.getColumnIndex(TASK_COLUMN_DETAIL)));
            task.setEndDate(c.getString(c.getColumnIndex(TASK_COLUMN_ENDDATE)));
            task.setStatus(c.getInt(c.getColumnIndex(TASK_COLUMN_STATUS)));
            task.setManDays(c.getFloat(c.getColumnIndex(TASK_COLUMN_MANDAYS)));
            task.setCreatedate(c.getString(c.getColumnIndex(TASK_COLUMN_CREATEDATE)));

            list.add(task);

            c.moveToNext();
        }

        return list;

    }

    //select
    public List<Task> getListByAnkenId(int ankenId){

        List<Task> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + TASK_COLUMN_ANKENID + " = " + ankenId + " ORDER BY " + TASK_COLUMN_ID + " ASC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Task task = new Task();
            task.setId(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setDetail(c.getString(c.getColumnIndex(TASK_COLUMN_DETAIL)));
            task.setEndDate(c.getString(c.getColumnIndex(TASK_COLUMN_ENDDATE)));
            task.setManDays(c.getFloat(c.getColumnIndex(TASK_COLUMN_MANDAYS)));
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

            task.setId(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));
            task.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            task.setAnkenId(c.getInt(c.getColumnIndex(TASK_COLUMN_ANKENID)));
            task.setDetail(c.getString(c.getColumnIndex(TASK_COLUMN_DETAIL)));
            task.setEndDate(c.getString(c.getColumnIndex(TASK_COLUMN_ENDDATE)));
            task.setStatus(c.getInt(c.getColumnIndex(TASK_COLUMN_STATUS)));
            task.setManDays(c.getFloat(c.getColumnIndex(TASK_COLUMN_MANDAYS)));
            task.setCreatedate(c.getString(c.getColumnIndex(TASK_COLUMN_CREATEDATE)));

            c.moveToNext();
        }


        return task;

    }
    //add
    public long addTask(Task task){

        Common.log("getTaskName:" + task.getTaskName());
        Common.log("getDetail:" + task.getDetail());
        Common.log("getAnkenId:" + task.getAnkenId());
        Common.log("getStatus:" + task.getStatus());
        Common.log("getManDays:" + task.getManDays());
        Common.log("getEndDate:" + task.getEndDate());

        ContentValues values = new ContentValues();
        values.put(TASK_COLUMN_NAME, task.getTaskName());
        values.put(TASK_COLUMN_DETAIL, task.getDetail());
        values.put(TASK_COLUMN_ANKENID, task.getAnkenId());
        values.put(TASK_COLUMN_STATUS, task.getStatus());
        values.put(TASK_COLUMN_MANDAYS, task.getManDays());
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


    /**
     * task history
     */
    public List<TaskHistory> getTaskHistoryByTaskId(int taskId){
        List<TaskHistory> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TASKHISTORY + " WHERE " + TASKHISTORY_TASK_ID + " = " + taskId;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            TaskHistory history = new TaskHistory();
            history.setId(c.getInt(c.getColumnIndex(TASKHISTORY_ID)));
            history.setTaskId(c.getInt(c.getColumnIndex(TASKHISTORY_TASK_ID)));
            history.setTargetdate(c.getString(c.getColumnIndex(TASKHISTORY_TARGETDATE)));
            history.setContent(c.getString(c.getColumnIndex(TASKHISTORY_CONTENTS)));
            history.setManDay(c.getFloat(c.getColumnIndex(TASKHISTORY_MANDAY)));
            history.setCreatedate(c.getString(c.getColumnIndex(TASKHISTORY_CREATEDATE)));

            list.add(history);

            c.moveToNext();
        }

        return list;
    }

    /**
     * task history
     */
    public TaskHistory getTaskHistoryByTaskHistoryId(int taskHistoryId){

        String query = "SELECT * FROM " + TABLE_TASKHISTORY + " WHERE " + TASKHISTORY_ID + " = " + taskHistoryId;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        TaskHistory history = new TaskHistory();

        while(!c.isAfterLast()){


            history.setId(c.getInt(c.getColumnIndex(TASKHISTORY_ID)));
            history.setTaskId(c.getInt(c.getColumnIndex(TASKHISTORY_TASK_ID)));
            history.setTargetdate(c.getString(c.getColumnIndex(TASKHISTORY_TARGETDATE)));
            history.setContent(c.getString(c.getColumnIndex(TASKHISTORY_CONTENTS)));
            history.setManDay(c.getFloat(c.getColumnIndex(TASKHISTORY_MANDAY)));
            history.setCreatedate(c.getString(c.getColumnIndex(TASKHISTORY_CREATEDATE)));

            c.moveToNext();
        }

        return history;
    }


    public long addTaskHistory(TaskHistory history){

        ContentValues values = new ContentValues();
        values.put(TASKHISTORY_TASK_ID, history.getTaskId());
        values.put(TASKHISTORY_CONTENTS, history.getContent());
        values.put(TASKHISTORY_TARGETDATE, history.getTargetdate());
        values.put(TASKHISTORY_MANDAY, history.getManDay());
        values.put(TASKHISTORY_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();

        long insertId = db.insert(TABLE_TASKHISTORY,null, values);

        return insertId;

    }

    public long updateTaskHistory(TaskHistory history){

        ContentValues values = new ContentValues();
        values.put(TASKHISTORY_TASK_ID, history.getTaskId());
        values.put(TASKHISTORY_CONTENTS, history.getContent());
        values.put(TASKHISTORY_TARGETDATE, history.getTargetdate());
        values.put(TASKHISTORY_MANDAY, history.getManDay());
        values.put(TASKHISTORY_CREATEDATE, history.getCreatedate());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = {String.valueOf(history.getId())};

        long insertId = db.update(TABLE_TASKHISTORY, values, TASKHISTORY_ID + " = ? ", args);

        return insertId;

    }

    public void deleteTaskHistory(int id){
        String query = "DELETE FROM " + TABLE_TASKHISTORY + " WHERE " + TASKHISTORY_ID + " = " + id;
        SQLiteDatabase db  =getWritableDatabase();
        db.execSQL(query);
    }


}
