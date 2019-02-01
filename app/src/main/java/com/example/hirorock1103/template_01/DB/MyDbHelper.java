package com.example.hirorock1103.template_01.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    private final static int DBVERSION = 28;
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
    protected final static String ANKEN_COLUMN_PRICE = "price";
    protected final static String ANKEN_COLUMN_ISCOMPLETE = "isComplete";
    protected final static String ANKEN_COLUMN_CREATEDATE = "createdate";

    protected final static String TABLE_ANKEN_TYPE = "AnkenType";
    protected final static String TYPE_COLUMN_ID = "id";
    protected final static String TYPE_COLUMN_TYPENAME = "typeName";
    protected final static String TYPE_COLUMN_COLORCODE = "colorCode";
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
    protected final static String TASK_COLUMN_STATUS = "status";
    protected final static String TASK_COLUMN_MANDAYS = "man_days";
    protected final static String TASK_COLUMN_CREATEDATE = "createdate";

    protected final static String TABLE_LEARN = "Learn";
    protected final static String LEARN_COLUMN_ID = "id";
    protected final static String LEARN_COLUMN_TITLE = "learnTitle";
    protected final static String LEARN_COLUMN_ANKENID = "anken_id";
    protected final static String LEARN_COLUMN_STATUS = "status";
    protected final static String LEARN_COLUMN_CREATEDATE = "createdate";

    protected final static String TABLE_LEARN_MEMO = "LearnMemo";
    protected final static String LEARN_COLUMN_MEMO_ID = "id";
    protected final static String LEARN_COLUMN_MEMO_LERANID = "learnId";
    protected final static String LEARN_COLUMN_MEMO_TYPE = "type";
    protected final static String LEARN_COLUMN_MEMO_CONTENTS = "memo";


    protected final static String TABLE_TASKHISTORY = "TaskHistory";
    protected final static String TASKHISTORY_ID = "id";
    protected final static String TASKHISTORY_TASK_ID = "task_id";
    protected final static String TASKHISTORY_CONTENTS = "content";
    protected final static String TASKHISTORY_TARGETDATE = "targetdate";
    protected final static String TASKHISTORY_MANDAY = "man_day";
    protected final static String TASKHISTORY_CREATEDATE = "createdate";


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
                ANKEN_COLUMN_PRICE + " integer default 0, " +
                ANKEN_COLUMN_ISCOMPLETE + " integer default 0, " +
                ANKEN_COLUMN_CREATEDATE + " text " +
                ")";

        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKHISTORY + "(" +
                TASKHISTORY_ID + " integer primary key autoincrement, " +
                TASKHISTORY_TASK_ID + " integer default 0, " +
                TASKHISTORY_TARGETDATE + " text, " +
                TASKHISTORY_CONTENTS + " text, " +
                TASKHISTORY_MANDAY + " real default 0, " +
                TASKHISTORY_CREATEDATE + " text " +
                ")";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_ANKEN_TYPE + "(" +
                TYPE_COLUMN_ID + " integer primary key autoincrement, " +
                ANKEN_COLUMN_TYPE + " integer default 0, " +
                TYPE_COLUMN_TYPENAME + " text, " +
                TYPE_COLUMN_COLORCODE + " text, " +
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
                TASK_COLUMN_MANDAYS + " float default 0, " +
                TASK_COLUMN_STATUS + " integer default 0, " +
                TASK_COLUMN_ENDDATE + " text, " +
                TASK_COLUMN_CREATEDATE + " text " +
                ")";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_LEARN + "(" +
                LEARN_COLUMN_ID + " integer primary key autoincrement, " +
                LEARN_COLUMN_TITLE + " text, " +
                LEARN_COLUMN_ANKENID + " integer, " +
                LEARN_COLUMN_STATUS + " integer default 0, " +
                LEARN_COLUMN_CREATEDATE + " text " +
                ")";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_LEARN_MEMO + "(" +
                LEARN_COLUMN_MEMO_ID + " integer primary key autoincrement, " +
                LEARN_COLUMN_MEMO_LERANID + " integer, " +
                LEARN_COLUMN_MEMO_TYPE + " integer default 0, " +
                LEARN_COLUMN_MEMO_CONTENTS + " text " +
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
        query = "DROP TABLE IF EXISTS " + TABLE_LEARN;
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_LEARN_MEMO;
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_TASKHISTORY;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
