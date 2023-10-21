package com.example.zooalarm.database;

public class AlarmDbSchema {
    public static final class AlarmTable{
        public static final String NAME= "alarms";
        public static final class Cols{
            public static final String UUID ="uuid";
            public static final String TIME = "time";
            public static final String REPEAT = "repeat";
            public static final String ISON = "is_on";
            public static final String ACTIVITY = "activity";

        }
    }
}
