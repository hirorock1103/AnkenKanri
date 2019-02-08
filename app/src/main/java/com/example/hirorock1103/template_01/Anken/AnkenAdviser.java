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


    //案件ごとの問題を取得する
    public void setMsgEachAnken(){

        List<Anken> list = new ArrayList<>();

        //案件の状態が完了になっているものは除外
        AnkenManager ankenManager = new AnkenManager(context);
        list = ankenManager.getListByIsComplete(0);

        for (int i = 0; i < list.size(); i++){

            Anken anken = list.get(i);

            //開始日が7日以上の案件は除外する
            if(anken.getStartDate() != null && anken.getStartDate().isEmpty() == false){
                int diff = Common.getDateDiff(Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2) , anken.getStartDate(), Common.DATE_FORMAT_SAMPLE_2);

                if(diff > 7){
                    continue;
                }

                List<String> errList = this.getErrorMsg(anken.getId());

                StringBuilder builder = new StringBuilder();
                if(errList.size() > 0){
                    builder.append("『" + anken.getAnkenName() + "』に関してのアラート\n");
                    int n = 1;
                    for (String err : errList){
                        builder.append("(" + n + ") " + err);
                    }

                    msg.add(builder.toString());
                }

            }


        }



    }

    public List<String> getErrorMsg(int ankenId){

        List<String> errList = new ArrayList<>();

        AnkenManager ankenManager = new AnkenManager(context);
        TaskManager taskManager = new TaskManager(context);
        Anken anken = ankenManager.getListByID(ankenId);
        List<Task> taskList = taskManager.getListByAnkenId(ankenId);
        //List<TaskHistory> taskHistoryList = taskManager.getTaskHistoryByTaskId()

        //もうすぐ案件スタートするけどタスクのセット状況が５０パーセント以下
        int taskSetSituation = 0;//予定工数とタスク工数
        float ankenManday = anken.getManDay();
        float setTaskManday = 0;
        for(Task task : taskList){
            setTaskManday += task.getManDays();
        }

        if(ankenManday > 0){
            taskSetSituation = (int)((setTaskManday / ankenManday) * 100);
            if(taskSetSituation < 50){
                errList.add("タスクの設定状況が" + taskSetSituation + "%です！案件開始日までにタスクを設定する必要があります！");
            }else if(taskSetSituation > 100){
                errList.add("タスクの設定状況が100%を超えています！タスクの工数を見直すか案件の工数を見直す必要があります！");
            }
        }


        return errList;


    }


    //
    public List<String> getMessageList(){

        return msg;

    }





}
