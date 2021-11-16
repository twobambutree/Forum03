package com.example.forum2.Models;

public class InfoComplaint {
    public String comSubject;
    public String comDetail;
    public int curDate;

    public InfoComplaint(String comSubject, String comDetail, int curDate){
        this.comSubject = comSubject;
        this.comDetail = comDetail;
        this.curDate = curDate;
    }
}
