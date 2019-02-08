package com.example.hirorock1103.template_01.Common;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {


    public static String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_SAMPLE_1 = "yyyy-MM-dd";
    public static String DATE_FORMAT_SAMPLE_2 = "yyyy/MM/dd";

    /**
     * 日付変更
     * @param str
     * @param pattern1
     * @param patten2
     * @return
     */
    public static String formatStrToDate(String str, String pattern1 , String patten2){

        String formatdate = "";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern1);
        SimpleDateFormat tosdf = new SimpleDateFormat(patten2);

        try {
            Date date = sdf.parse(str);
            formatdate = tosdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatdate;

    }

    public static String formatDate(Date date , String pattern){

        String formatdate = "";
        SimpleDateFormat tosdf = new SimpleDateFormat(pattern);
        formatdate = tosdf.format(date);

        return formatdate;
    }

    /**
     * 日付加算　減算
     */
    public static Date addDateFromToday(String target, int value){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Date date = new Date();

        switch (target){
            case "MONTH":
                calendar.add(Calendar.MONTH, value);
                break;
            case "YEAR":
                calendar.add(Calendar.YEAR, value);
                break;
            case "DAY":
                calendar.add(Calendar.DATE, value);
                break;
        }

        date = calendar.getTime();


        return date;

    }


    /**
     * differece between 2 date
     */
    public static int getDateDiff(String from, String to, String format){

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dateTo = null;
        Date dateFrom = null;

        // Date型に変換
        try {
            dateFrom = sdf.parse(from);
            dateTo = sdf.parse(to);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // 差分の日数を計算する
        long dateTimeTo = dateTo.getTime();
        long dateTimeFrom = dateFrom.getTime();
        long dayDiff = ( dateTimeTo - dateTimeFrom  ) / (1000 * 60 * 60 * 24 );


        return (int)dayDiff;

    }

    /**
     * 月末を取得　任意のパターンで 参考：https://qiita.com/todogzm/items/bb8c144ba17e6de392a3
     */
    public static String getLastDate(int targetYear, int targetMonth, String pattern){

        String lastdate = "";

        Calendar calendar = Calendar.getInstance();
        calendar.set(targetYear,targetMonth-1, 1, 0, 0, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MILLISECOND, -1);

        Date date = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        lastdate = sdf.format(date);

        return lastdate;
    }

    /**
     * 月初を取得　任意のパターンで
     */
    public static String getFirstDate(int targetYear, int targetMonth, String pattern){

        String firstdate = "";

        Calendar calendar = Calendar.getInstance();
        calendar.set(targetYear,targetMonth-1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date date = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        firstdate = sdf.format(date);

        return firstdate;
    }


    /**
     * log
     */
    public static void log(String str){

        Log.i("INFO_TEST", str);

    }

    /**
     * toast
     */
    public static void toast(Context context , String str){

        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

    }

    public static class ActionManager{

        public void setKeyBoardHide(Context context, ConstraintLayout layout){

            InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(layout.getWindowToken(), inputMethodManager.HIDE_NOT_ALWAYS);
            layout.requestFocus();

        }
    }

    public static void sendMail(Context context,String subject, String contents){
        Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(contents));
        emailIntent.setType("text/html");
        context.startActivity(Intent.createChooser(emailIntent,
                "Send Email Using: "));
    }




}
