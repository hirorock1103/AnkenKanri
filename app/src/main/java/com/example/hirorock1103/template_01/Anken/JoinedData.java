package com.example.hirorock1103.template_01.Anken;

import java.util.List;

public class JoinedData {

    public static class AnkenHasTask{

        private int id;
        private int ankenId;
        private String ankenName;
        private List<Task> taskList;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAnkenId() {
            return ankenId;
        }

        public void setAnkenId(int ankenId) {
            this.ankenId = ankenId;
        }

        public String getAnkenName() {
            return ankenName;
        }

        public void setAnkenName(String ankenName) {
            this.ankenName = ankenName;
        }

        public List<Task> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<Task> taskList) {
            this.taskList = taskList;
        }
    }

    public static class AnkenHasMileStone{

        private int id;
        private int ankenId;
        private String ankenName;
        private String ankenTypeName;
        private List<MileStone> mileStonesList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAnkenId() {
            return ankenId;
        }

        public void setAnkenId(int ankenId) {
            this.ankenId = ankenId;
        }

        public String getAnkenName() {
            return ankenName;
        }

        public void setAnkenName(String ankenName) {
            this.ankenName = ankenName;
        }

        public List<MileStone> getMileStonesList() {
            return mileStonesList;
        }

        public void setMileStonesList(List<MileStone> mileStonesList) {
            this.mileStonesList = mileStonesList;
        }

        public String getAnkenTypeName() {
            return ankenTypeName;
        }

        public void setAnkenTypeName(String ankenTypeName) {
            this.ankenTypeName = ankenTypeName;
        }
    }

    public static class AnkenTaskHistory{

        private int id;
        private int taskId;
        private int ankenId;
        private String taskName;
        private String ankenName;
        private String ankenEndDate;
        private String taskEndDate;
        private float taskManday;
        private float ankenManday;
        private float historyManday;
        private String contents;
        private String historyTargetDate;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getAnkenId() {
            return ankenId;
        }

        public void setAnkenId(int ankenId) {
            this.ankenId = ankenId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getAnkenName() {
            return ankenName;
        }

        public void setAnkenName(String ankenName) {
            this.ankenName = ankenName;
        }

        public String getAnkenEndDate() {
            return ankenEndDate;
        }

        public void setAnkenEndDate(String ankenEndDate) {
            this.ankenEndDate = ankenEndDate;
        }

        public String getTaskEndDate() {
            return taskEndDate;
        }

        public void setTaskEndDate(String taskEndDate) {
            this.taskEndDate = taskEndDate;
        }

        public float getTaskManday() {
            return taskManday;
        }

        public void setTaskManday(float taskManday) {
            this.taskManday = taskManday;
        }

        public float getAnkenManday() {
            return ankenManday;
        }

        public void setAnkenManday(float ankenManday) {
            this.ankenManday = ankenManday;
        }

        public float getHistoryManday() {
            return historyManday;
        }

        public void setHistoryManday(float historyManday) {
            this.historyManday = historyManday;
        }

        public String getHistoryTargetDate() {
            return historyTargetDate;
        }

        public void setHistoryTargetDate(String historyTargetDate) {
            this.historyTargetDate = historyTargetDate;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }
    }

    public static class ValidTask{

        private int id;
        private int taskId;
        private int ankenId;
        private int ankenStatus;
        private int taskStatus;
        private float taskManday;
        private String ankenName;
        private String taskName;
        private String taskContents;
        private String ankenEndDate;
        private String taskEndDate;
        private boolean isTodayHistory;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getAnkenId() {
            return ankenId;
        }

        public void setAnkenId(int ankenId) {
            this.ankenId = ankenId;
        }

        public int getAnkenStatus() {
            return ankenStatus;
        }

        public void setAnkenStatus(int ankenStatus) {
            this.ankenStatus = ankenStatus;
        }

        public int getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(int taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getAnkenName() {
            return ankenName;
        }

        public void setAnkenName(String ankenName) {
            this.ankenName = ankenName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getAnkenEndDate() {
            return ankenEndDate;
        }

        public void setAnkenEndDate(String ankenEndDate) {
            this.ankenEndDate = ankenEndDate;
        }

        public String getTaskEndDate() {
            return taskEndDate;
        }

        public void setTaskEndDate(String taskEndDate) {
            this.taskEndDate = taskEndDate;
        }

        public boolean isTodayHistory() {
            return isTodayHistory;
        }

        public void setTodayHistory(boolean todayHistory) {
            isTodayHistory = todayHistory;
        }

        public float getTaskManday() {
            return taskManday;
        }

        public void setTaskManday(float taskManday) {
            this.taskManday = taskManday;
        }

        public String getTaskContents() {
            return taskContents;
        }

        public void setTaskContents(String taskContents) {
            this.taskContents = taskContents;
        }
    }

}
