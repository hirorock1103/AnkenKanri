package com.example.hirorock1103.template_01.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    private final static int DBVERSION = 15;
    private final static String DBNAME = "Anken.db";

    protected final static String TABLE_ANKEN_NAME = "Anken";
    protected final static String ANKEN_COLUMN_ID = "id";
    protected final static String ANKEN_COLUMN_TITLE = "ankenName";
    protected final static String ANKEN_COLUMN_TYPE = "ankenType";//未使用
    protected final static String ANKEN_COLUMN_TYPENAME = "ankenTypeName";
    protected final static String ANKEN_COLUMN_START = "startDate";
    protected final static String ANKEN_COLUMN_END = "endDate";
    protected final static String ANKEN_COLUMN_BUDGET = "budget";
    protected final static String ANKEN_COLUMN_MANDAY = "man_day";
    protected final static String ANKEN_COLUMN_ISCOMPLETE = "isComplete";
    protected final static String ANKEN_COLUMN_CREATEDATE = "createdate";

    protected final static String TABLE_ANKEN_TYPE = "AnkenType";
    protected final static String TYPE_COLUMN_ID = "id";
    protected final static String TYPE_COLUMN_TYPENAME = "typeName";
    protected final static String TYPE_COLUMN_STATUS = "status";

    protected final static String TABLE_MILESTONE = "MileStone";
    protected final static String MILESTONE_COLUMN_ID = "id";
    protected final static String MILESTONE_COLUMN_NAME = "milestone_name";
    protected final static String MILESTONE_COLUMN_DETAIL = "detail";
    protected final static String MILESTONE_COLUMN_ANKENID = "anken_id";
    protected final static String MILESTONE_COLUMN_ENDDATE = "endDate";
    protected final static String MILESTONE_COLUMN_STATUS = "status";
    protected final static String MILESTONE_COLUMN_CREATEDATE = "createdate";

    protected final static String TABLE_TASK = "Task";
    protected final static String TASK_COLUMN_ID = "id";
    protected final static String TASK_COLUMN_NAME = "task_name";
    protected final static String TASK_COLUMN_ANKENID = "anken_id";
    protected final static String TASK_COLUMN_DETAIL = "detail";
    protected final static String TASK_COLUMN_ENDDATE = "endDate";
    protected final static String TASK_COLUMN_ISCOMPLETE = "is_complete";
    protected final static String TASK_COLUMN_CREATEDATE = "createdate";



    public MyDbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_ANKEN_NAME + "(" +
                ANKEN_COLUMN_ID + " integer primary key autoincrement, " +
                ANKEN_COLUMN_TYPE + " integer default 0, " +
                ANKEN_COLUMN_TYPENAME + " text, " +
                ANKEN_COLUMN_TITLE + " text, " +
                ANKEN_COLUMN_START + " text default null, " +
                ANKEN_COLUMN_END + " text default null, " +
                ANKEN_COLUMN_BUDGET + " integer default 0, " +
                ANKEN_COLUMN_MANDAY + " real default 0, " +
                ANKEN_COLUMN_ISCOMPLETE + " integer default 0, " +
                ANKEN_COLUMN_CREATEDATE + " text " +
                ")";

        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_ANKEN_TYPE + "(" +
                TYPE_COLUMN_ID + " integer primary key autoincrement, " +
                ANKEN_COLUMN_TYPE + " integer default 0, " +
                TYPE_COLUMN_TYPENAME + " text, " +
                TYPE_COLUMN_STATUS + " integer default 0 " +
                ")";
        sqLiteDatabase.execSQL(query);


        query = "CREATE TABLE IF NOT EXISTS " + TABLE_MILESTONE + "(" +
                MILESTONE_COLUMN_ID + " integer primary key autoincrement, " +
                MILESTONE_COLUMN_NAME + " text, " +
                MILESTONE_COLUMN_DETAIL + " text, " +
                MILESTONE_COLUMN_ANKENID + " integer, " +
                MILESTONE_COLUMN_STATUS + " integer default 0, " +
                MILESTONE_COLUMN_ENDDATE + " text, " +
                MILESTONE_COLUMN_CREATEDATE + " text " +
                ")";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + "(" +
                TASK_COLUMN_ID + " integer primary key autoincrement, " +
                TASK_COLUMN_NAME + " text, " +
                TASK_COLUMN_DETAIL + " text, " +
                TASK_COLUMN_ANKENID + " integer, " +
                TASK_COLUMN_ISCOMPLETE + " integer default 0, " +
                TASK_COLUMN_ENDDATE + " text, " +
                TASK_COLUMN_CREATEDATE + " text " +
                ")";
        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_ANKEN_NAME;
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_ANKEN_TYPE;
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_MILESTONE;
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_TASK;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
