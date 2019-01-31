package com.example.hirorock1103.template_01.Anken;

import java.util.List;

public class JoinedData {

    public static class AnkenHasMileStone{

        private int id;
        private int ankenId;
        private String ankenName;
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
    }

    public static class ValidTask{

        private int id;
        private int taskId;
        private int ankenId;
        private int ankenStatus;
        private int taskStatus;
        private String ankenName;
        private String taskName;
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
    }

}
