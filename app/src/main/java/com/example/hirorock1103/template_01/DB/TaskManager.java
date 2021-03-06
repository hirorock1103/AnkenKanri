package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Anken.JoinedData;
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

    public List<JoinedData.ValidTask> getAllValidTasks(){

        List<JoinedData.ValidTask> list = new ArrayList<>();

        String query = "SELECT *," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_END + " as ankenEndDate," +
                TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " as taskEndDate," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_ID + " as ankenId," +
                TABLE_TASK + "." + TASK_COLUMN_DETAIL + " as taskContents," +
                TABLE_TASK + "." + TASK_COLUMN_ID + " as taskId " +
                " FROM " + TABLE_TASK
                + " INNER JOIN " + TABLE_ANKEN_NAME
                + " ON " + TABLE_TASK + "." + TASK_COLUMN_ANKENID + "=" + TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_ID
                + " WHERE " + ANKEN_COLUMN_ISCOMPLETE + " = 0 "
                + " ORDER BY " + TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " ASC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            JoinedData.ValidTask validTask = new JoinedData.ValidTask();

            validTask.setId(1);
            validTask.setAnkenId(c.getInt(c.getColumnIndex("ankenId")));
            validTask.setTaskId(c.getInt(c.getColumnIndex("taskId")));
            validTask.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            validTask.setAnkenEndDate(c.getString(c.getColumnIndex("ankenEndDate")));
            validTask.setTaskEndDate(c.getString(c.getColumnIndex("taskEndDate")));
            validTask.setTaskContents(c.getString(c.getColumnIndex("taskContents")));
            validTask.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));

            list.add(validTask);

            c.moveToNext();
        }

        return list;

    }



    public List<JoinedData.AnkenTaskHistory> getAnkenHistory(String from ,String to){
        List<JoinedData.AnkenTaskHistory> list = new ArrayList<>();

        String query = "SELECT * , " +
                TABLE_TASKHISTORY + "." + TASKHISTORY_ID + " as id," +
                TABLE_TASKHISTORY + "." + TASKHISTORY_CONTENTS + " as historyContents," +
                TABLE_TASKHISTORY + "." + TASKHISTORY_MANDAY + " as taskHistoryManday," +
                TABLE_TASKHISTORY + "." + TASKHISTORY_TARGETDATE + " as taskHistoryTargetdate," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_ID + " as ankenId," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_TITLE + " as ankenName," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_MANDAY + " as ankenManday," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_END + " as ankenEnddate," +
                TABLE_TASK + "." + TASK_COLUMN_ID + " as taskId," +
                TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " as taskEndDate," +
                TABLE_TASK + "." + TASK_COLUMN_MANDAYS + " as taskManday," +
                TABLE_TASK + "." + TASK_COLUMN_NAME + " as taskName " +
                " FROM " + TABLE_TASKHISTORY + " INNER JOIN " + TABLE_TASK +
                " ON " + TABLE_TASKHISTORY + "." + TASKHISTORY_TASK_ID + " = " + TABLE_TASK + "." + TASK_COLUMN_ID +
                " INNER JOIN " + TABLE_ANKEN_NAME +
                " ON " + TABLE_TASK + "." + TASK_COLUMN_ANKENID + " = " + TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_ID
                + " WHERE " + TABLE_TASKHISTORY + "." + TASKHISTORY_ID + " > 0 ";


        String conditions = "";
        if(from.isEmpty()==false || to.isEmpty()==false){
            conditions = "(";
            if(from.isEmpty() == false){
                conditions += TABLE_TASKHISTORY + "." + TASKHISTORY_TARGETDATE + " >= " + "'"+from+"'";
            }

            if(to.isEmpty() == false){
                if(from.isEmpty()){
                    conditions += TABLE_TASKHISTORY + "." + TASKHISTORY_TARGETDATE + " <= " + "'"+to+"'";
                }else{
                    conditions +=  " AND " + TABLE_TASKHISTORY + "." + TASKHISTORY_TARGETDATE + " <= " + "'"+to+"'";
                }
            }
            conditions += ")";

            query += " AND " + conditions;

        }

        query += " ORDER BY " + TABLE_TASKHISTORY + "." + TASKHISTORY_ID + " ASC ";

        Common.log(query);

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            JoinedData.AnkenTaskHistory taskHistory = new JoinedData.AnkenTaskHistory();

            taskHistory.setId(c.getInt(c.getColumnIndex("id")));
            taskHistory.setAnkenId(c.getInt(c.getColumnIndex("ankenId")));
            taskHistory.setTaskId(c.getInt(c.getColumnIndex("taskId")));
            taskHistory.setAnkenName(c.getString(c.getColumnIndex("ankenName")));
            taskHistory.setAnkenEndDate(c.getString(c.getColumnIndex("ankenEnddate")));
            taskHistory.setAnkenManday(c.getFloat(c.getColumnIndex("ankenManday")));
            taskHistory.setTaskName(c.getString(c.getColumnIndex("taskName")));
            taskHistory.setTaskEndDate(c.getString(c.getColumnIndex("taskEndDate")));
            taskHistory.setTaskManday(c.getFloat(c.getColumnIndex("taskManday")));
            taskHistory.setHistoryManday(c.getFloat(c.getColumnIndex("taskHistoryManday")));
            taskHistory.setHistoryTargetDate(c.getString(c.getColumnIndex("taskHistoryTargetdate")));
            taskHistory.setContents(c.getString(c.getColumnIndex("historyContents")));


            list.add(taskHistory);

            c.moveToNext();
        }


        return list;


    }



    public List<JoinedData.ValidTask> getAllValidTasksBySpan(String from, String to){

        List<JoinedData.ValidTask> list = new ArrayList<>();

        String query = "SELECT *," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_END + " as ankenEndDate," +
                TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " as taskEndDate," +
                TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_ID + " as ankenId," +
                TABLE_TASK + "." + TASK_COLUMN_MANDAYS + " as taskManday," +
                TABLE_TASK + "." + TASK_COLUMN_DETAIL + " as taskContents," +
                TABLE_TASK + "." + TASK_COLUMN_ID + " as taskId " +
                " FROM " + TABLE_TASK
                + " INNER JOIN " + TABLE_ANKEN_NAME
                + " ON " + TABLE_TASK + "." + TASK_COLUMN_ANKENID + "=" + TABLE_ANKEN_NAME + "." + ANKEN_COLUMN_ID
                + " WHERE " + ANKEN_COLUMN_ISCOMPLETE + " = 0 ";

        String conditions = "";
        if(from.isEmpty()==false || to.isEmpty()==false){
            conditions = "(";
            if(from.isEmpty() == false){
                conditions += TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " >= " + "'"+from+"'";
            }

            if(to.isEmpty() == false){
                if(from.isEmpty()){
                    conditions += TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " <= " + "'"+to+"'";
                }else{
                    conditions +=  " AND " + TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " <= " + "'"+to+"'";
                }
            }
            conditions += ")";

            query += " AND " + conditions;

        }


        Common.log("conditions : " + conditions);

        query += " ORDER BY " + TABLE_TASK + "." + TASK_COLUMN_ENDDATE + " ASC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            JoinedData.ValidTask validTask = new JoinedData.ValidTask();

            validTask.setId(1);
            validTask.setAnkenId(c.getInt(c.getColumnIndex("ankenId")));
            validTask.setTaskId(c.getInt(c.getColumnIndex("taskId")));
            validTask.setTaskManday(c.getFloat(c.getColumnIndex("taskManday")));
            validTask.setTaskName(c.getString(c.getColumnIndex(TASK_COLUMN_NAME)));
            validTask.setAnkenEndDate(c.getString(c.getColumnIndex("ankenEndDate")));
            validTask.setTaskEndDate(c.getString(c.getColumnIndex("taskEndDate")));
            validTask.setTaskContents(c.getString(c.getColumnIndex("taskContents")));
            validTask.setAnkenName(c.getString(c.getColumnIndex(ANKEN_COLUMN_TITLE)));

            list.add(validTask);

            c.moveToNext();
        }

        return list;

    }

    //select
    public List<Task> getListByAnkenId(int ankenId){

        List<Task> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + TASK_COLUMN_ANKENID + " = " + ankenId + " ORDER BY " + " ( " + TASK_COLUMN_STATUS + " AND " + TASK_COLUMN_ID + ")ASC ";
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

    //select
    public List<Task> getListByAnkenIdAndStatus(int ankenId, int status){

        List<Task> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TASK
                + " WHERE " + TASK_COLUMN_ANKENID + " = " + ankenId + " AND " + TASK_COLUMN_STATUS + " = " + status
                + " ORDER BY " + TASK_COLUMN_ID + " ASC ";

        Common.log("query" + query);

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

    //getTaskHoursByAnkenId
    public float getTaskHoursByAnkenId(int ankenId){

        float mandays = 0;

        String query = "SELECT SUM(" +TASK_COLUMN_MANDAYS+ ") as total_mandays FROM " + TABLE_TASK
                + " WHERE " + TASK_COLUMN_ANKENID + " = " + ankenId;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            mandays = c.getFloat(c.getColumnIndex("total_mandays"));

            c.moveToNext();

        }


        return mandays;

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
        values.put(TASK_COLUMN_MANDAYS, task.getManDays());
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

        //delete task history
        query = "DELETE FROM " + TABLE_TASKHISTORY + " WHERE " + TASKHISTORY_TASK_ID + " = " + id;
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

    //消費工数を取得する
    public float getTaskHistoryMandaysByTaskId(int taskId){

        String query = "SELECt SUM(" + TASKHISTORY_MANDAY +") as totalManday FROM " + TABLE_TASKHISTORY +
                " WHERE " + TASKHISTORY_TASK_ID + " = " + taskId ;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        float manday = 0;

        while(!c.isAfterLast()){

            manday = c.getFloat(c.getColumnIndex("totalManday"));
            c.moveToNext();
        }

        return manday;

    }

    //消費工数を取得する
    public float getTaskHistoryMandaysByAnkenId(int ankenId){

        String query = "SELECt SUM(" + TASKHISTORY_MANDAY +") as totalManday FROM " + TABLE_TASKHISTORY +
                " INNER JOIN " + TABLE_TASK + " ON " + TABLE_TASKHISTORY + "." + TASKHISTORY_TASK_ID + " = " +
                TABLE_TASK + "." + TASK_COLUMN_ID +
                " WHERE " + TABLE_TASK + "." + TASK_COLUMN_ANKENID + " = " + ankenId ;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        float manday = 0;

        while(!c.isAfterLast()){

            manday = c.getFloat(c.getColumnIndex("totalManday"));
            c.moveToNext();
        }

        return manday;

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
