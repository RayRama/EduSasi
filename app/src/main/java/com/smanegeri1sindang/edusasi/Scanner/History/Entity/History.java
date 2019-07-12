package com.smanegeri1sindang.edusasi.Scanner.History.Entity;

import java.util.Comparator;

public class History {

    private int id;
    private String date;
    private String context;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public static Comparator<History> byDate = new Comparator<History>() {
        @Override
        public int compare(History o1, History o2) {
            return - Integer.valueOf(o1.id).compareTo(Integer.valueOf(o2.id));
        }
    };
}
