package com.example.hirorock1103.template_01.Anken;

import android.content.Context;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.MyDbHelper;
import com.example.hirorock1103.template_01.DB.TaskManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnkenAdviser {

    //setting
    private static final int READYDATEDIFF = 5;//スタート日の〇日前

    private Context context;
    private List<String> msg;


    public AnkenAdviser(Context context) {
        msg = new ArrayList<>();
        this.context = context;
    }


    //どの案件が一番やばい？

    //スタートするけど全然準備できてない案件は？
    public List<Anken> setMsgAnkenWhichIsNotReady(){

        List<Anken> list = new ArrayList<>();

        //案件のstartdateが〇日より前のものは除外
        //案件の状態が完了になっているものは除外
        AnkenManager ankenManager = new AnkenManager(context);
        list = ankenManager.getListByIsComplete(0);

        for(int i = 0; i < list.size(); i++){
            Anken anken = list.get(i);
            StringBuilder builder = new StringBuilder();
            if(anken.getStartDate() != null && anken.getStartDate().isEmpty() == false){
                int diff = Common.getDateDiff(Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2) , anken.getStartDate(), Common.DATE_FORMAT_SAMPLE_2);
                if(diff >= 0){
                    //開始している案件
                    builder.append(anken.getAnkenName() + "は既に開始している案件です。" );


                }else if(diff <= READYDATEDIFF){
                    //調査対象の案件
                    builder.append(anken.getAnkenName() + "は調査対象の案件です。" );
                }else{
                    //対象外なので除外
                    list.remove(i);
                }
            }else{
                builder.append(anken.getAnkenName() + "には開始日がセットされていないませんよ！" );
            }

            msg.add(builder.toString());

        }

        //分析 -- 予定工数に対する、設定タスクの割合が 〇〇%以下
        if(list.size() == 0){
            msg.add("準備できていない案件はありませんよ！順調です！");
        }


        return list;

    }

    //
    public List<String> getMessageList(){

        return msg;

    }





}
